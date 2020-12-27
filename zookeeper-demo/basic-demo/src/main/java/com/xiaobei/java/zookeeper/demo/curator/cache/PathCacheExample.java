package com.xiaobei.java.zookeeper.demo.curator.cache;

import com.xiaobei.java.zookeeper.demo.curator.framework.CreateClientExamples;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.KeeperException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * PathChildrenCache的示例。 示例“线束”是一个命令处理器，
 * 它允许在路径中添加/更新/删除节点。
 * 当更新发生时，PathChildrenCache会保留这些更改的缓存并输出。
 *
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/27 07:57
 */
public class PathCacheExample {

    private static final String PATH = "/data";

    public static void main(String[] args) {
        try (CuratorFramework client = CreateClientExamples
                .createSimple(CreateClientExamples.CONNECTION_STRING);
             // 在本例中我们将会缓存数据，注意这个操作是可选的
             PathChildrenCache cache = new PathChildrenCache(client, PATH, true)) {
            client.start();
            cache.start();
            processCommands(client, cache);
        } catch (IOException exception) {
            exception.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void processCommands(CuratorFramework client, PathChildrenCache cache) throws IOException, InterruptedException {
        //
        printHelp();
        List<?> servers = new ArrayList<>();
        try {
            addListener(cache);
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            boolean done = false;
            while (!done) {
                System.out.print(">");
                String line = in.readLine();
                if (line == null) {
                    break;
                }
                String command = line.trim();
                String[] parts = command.split("\\s");
                if (parts.length == 0) {
                    continue;
                }
                String operation = parts[0];
                String args[] = Arrays.copyOfRange(parts, 1, parts.length);
                if (operation.equalsIgnoreCase("help") || operation.equalsIgnoreCase("?")) {
                    printHelp();
                } else if (operation.equalsIgnoreCase("q") || operation.equalsIgnoreCase("quit")) {
                    done = true;
                } else if (operation.equals("set")) {
                    setValue(client, command, args);
                } else if (operation.equals("remove")) {
                    remove(client, command, args);
                } else if (operation.equals("list")) {
                    list(cache);
                }
                Thread.sleep(1000);// just to allow the console output to catch up
            }
        } catch (Exception e) {

        }

    }

    private static void list(PathChildrenCache cache) {
        if (cache.getCurrentData().size() == 0) {
            System.out.println("* empty *");
        } else {
            for (ChildData data : cache.getCurrentData()) {
                System.out.println(data.getPath() + " = " + new String(data.getData()));
            }
        }
    }

    private static void remove(CuratorFramework client, String command, String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("syntax error (expected remove <path>): " + command);
            return;
        }

        String name = args[0];
        if (name.contains("/")) {
            System.err.println("Invalid node name" + name);
            return;
        }
        String path = ZKPaths.makePath(PATH, name);
        try {
            client.delete().forPath(path);
        } catch (KeeperException.NoNodeException e) {
            // ignore
        }
    }

    private static void setValue(CuratorFramework client, String command, String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("syntax error (expected set <path> <value>): " + command);
            return;
        }
        String name = args[0];
        if (name.contains("/")) {
            System.err.println("Invalid node name：" + name);
            return;
        }
        String path = ZKPaths.makePath(PATH, name);

        byte[] bytes = args[1].getBytes();
        try {
            client.setData().forPath(path, bytes);
        } catch (KeeperException.NoNodeException e) {
            client.create().creatingParentContainersIfNeeded().forPath(path, bytes);
        }
    }

    private static void addListener(PathChildrenCache cache) {
        // a PathChildrenCacheListener is optional. Here, it's used just to log changes
        PathChildrenCacheListener listener = new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                String nodeFromPath = ZKPaths.getNodeFromPath(event.getData().getPath());
                switch (event.getType()) {
                    case CHILD_ADDED:
                        System.out.println("Node added: " + nodeFromPath);
                        break;
                    case CHILD_UPDATED:
                        System.out.println("Node changed: " + nodeFromPath);
                        break;
                    case CHILD_REMOVED:
                        System.out.println("Node removed: " + nodeFromPath);
                        break;

                }
            }
        };
        cache.getListenable().addListener(listener);
    }


    private static void printHelp() {
        System.out.println("An example of using PathChildrenCache. This example is driven by entering commands at the prompt:\n");
        System.out.println("set <name> <value>: Adds or updates a node with the given name");
        System.out.println("remove <name>: Deletes the node with the given name");
        System.out.println("list: List the nodes/values in the cache");
        System.out.println("quit: Quit the example");
        System.out.println();
    }

}
