package com.xiaobei.spring.web.demo;

import org.junit.Test;

/**
 * spring-test 对注解的支持：https://docs.spring.io/spring-framework/docs/5.3.1/reference/html/testing.html#integration-testing-annotations
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/05 21:56
 */
public class AnnotationsTest {


    /**
     * https://docs.spring.io/spring-framework/docs/5.3.1/reference/html/testing.html#integration-testing-annotations-spring
     *
     * TODO 完善各个注解的使用！！！
     * {@link org.springframework.test.context.BootstrapWith}
     *  https://docs.spring.io/spring-framework/docs/5.3.1/reference/html/testing.html#testcontext-bootstrapping
     * {@link org.springframework.test.context.ContextConfiguration}
     *  https://docs.spring.io/spring-framework/docs/5.3.1/reference/html/testing.html#testcontext-ctx-management
     *  https://docs.spring.io/spring-framework/docs/5.3.1/reference/html/testing.html#testcontext-junit-jupiter-nested-test-configuration
     * {@link org.springframework.test.context.web.WebAppConfiguration}
     *  https://docs.spring.io/spring-framework/docs/5.3.1/reference/html/testing.html#spring-testing-annotation-webappconfiguration
     * {@link org.springframework.test.context.ContextHierarchy}
     *  https://docs.spring.io/spring-framework/docs/5.3.1/reference/html/testing.html#spring-testing-annotation-contexthierarchy
     *  https://docs.spring.io/spring-framework/docs/5.3.1/reference/html/testing.html#testcontext-ctx-management-ctx-hierarchies
     *  https://docs.spring.io/spring-framework/docs/5.3.1/javadoc-api/org/springframework/test/context/ContextHierarchy.html
     * {@link org.springframework.test.context.ActiveProfiles}
     *  https://docs.spring.io/spring-framework/docs/5.3.1/reference/html/testing.html#testcontext-ctx-management-env-profiles
     *  https://docs.spring.io/spring-framework/docs/5.3.1/reference/html/testing.html#testcontext-junit-jupiter-nested-test-configuration
     *  https://docs.spring.io/spring-framework/docs/5.3.1/javadoc-api/org/springframework/test/context/ActiveProfiles.html
     * {@link org.springframework.test.context.TestPropertySource}
     *  https://docs.spring.io/spring-framework/docs/5.3.1/reference/html/testing.html#spring-testing-annotation-testpropertysource
     *  https://docs.spring.io/spring-framework/docs/5.3.1/reference/html/testing.html#testcontext-ctx-management-property-sources
     * {@link org.springframework.test.context.DynamicPropertySource}
     *  https://docs.spring.io/spring-framework/docs/5.3.1/reference/html/testing.html#spring-testing-annotation-dynamicpropertysource
     *  https://docs.spring.io/spring-framework/docs/5.3.1/reference/html/testing.html#testcontext-ctx-management-dynamic-property-sources
     * {@link org.springframework.test.annotation.DirtiesContext}
     *  https://docs.spring.io/spring-framework/docs/5.3.1/reference/html/testing.html#spring-testing-annotation-dirtiescontext
     *  https://docs.spring.io/spring-framework/docs/5.3.1/javadoc-api/org/springframework/test/annotation/DirtiesContext.HierarchyMode.html
     * {@link org.springframework.test.context.TestExecutionListeners}
     *  https://docs.spring.io/spring-framework/docs/5.3.1/reference/html/testing.html#spring-testing-annotation-testexecutionlisteners
     *  https://docs.spring.io/spring-framework/docs/5.3.1/reference/html/testing.html#testcontext-junit-jupiter-nested-test-configuration
     *  https://docs.spring.io/spring-framework/docs/5.3.1/javadoc-api/org/springframework/test/context/TestExecutionListeners.html
     * {@link org.springframework.test.annotation.Commit}
     *  https://docs.spring.io/spring-framework/docs/5.3.1/reference/html/testing.html#spring-testing-annotation-commit
     * {@link org.springframework.test.annotation.Rollback}
     *  https://docs.spring.io/spring-framework/docs/5.3.1/reference/html/testing.html#spring-testing-annotation-rollback
     * {@link org.springframework.test.context.transaction.BeforeTransaction}
     *  https://docs.spring.io/spring-framework/docs/5.3.1/reference/html/testing.html#spring-testing-annotation-beforetransaction
     * {@link org.springframework.test.context.transaction.AfterTransaction}
     *  https://docs.spring.io/spring-framework/docs/5.3.1/reference/html/testing.html#spring-testing-annotation-aftertransaction
     * {@link org.springframework.test.context.jdbc.Sql}
     *  https://docs.spring.io/spring-framework/docs/5.3.1/reference/html/testing.html#spring-testing-annotation-sql
     *  https://docs.spring.io/spring-framework/docs/5.3.1/reference/html/testing.html#testcontext-executing-sql-declaratively
     * {@link org.springframework.test.context.jdbc.SqlConfig}
     *  https://docs.spring.io/spring-framework/docs/5.3.1/reference/html/testing.html#spring-testing-annotation-sqlconfig
     * {@link org.springframework.test.context.jdbc.SqlMergeMode}
     *  https://docs.spring.io/spring-framework/docs/5.3.1/reference/html/testing.html#spring-testing-annotation-sqlmergemode
     * {@link org.springframework.test.context.jdbc.SqlGroup}
     *  https://docs.spring.io/spring-framework/docs/5.3.1/reference/html/testing.html#spring-testing-annotation-sqlgroup
     */
    @Test
    public void springTestingAnnotations() {

    }

    /**
     * TODO
     * {@link org.springframework.beans.factory.annotation.Autowired}
     * {@link org.springframework.beans.factory.annotation.Qualifier}
     * {@link javax.annotation.Resource}
     * {@link javax.annotation.ManagedBean}
     * {@link javax.inject.Inject}
     * {@link javax.inject.Named}
     * {@link javax.persistence.PersistenceContext }
     * {@link javax.persistence.PersistenceUnit  }
     * {@link org.springframework.beans.factory.annotation.Required}
     * {@link org.springframework.transaction.annotation.Transactional}
     *  https://docs.spring.io/spring-framework/docs/5.3.1/reference/html/testing.html#testcontext-tx-attribute-support
     */
    @Test
    public void standardAnnotationSupport() {

    }

    /**
     * {@link org.springframework.test.annotation.IfProfileValue}
     *  https://docs.spring.io/spring-framework/docs/5.3.1/reference/html/testing.html#integration-testing-annotations-junit4-ifprofilevalue
     * {@link org.springframework.test.annotation.ProfileValueSourceConfiguration}
     *  https://docs.spring.io/spring-framework/docs/5.3.1/reference/html/testing.html#integration-testing-annotations-junit4-profilevaluesourceconfiguration
     * {@link org.springframework.test.annotation.Timed}
     *  https://docs.spring.io/spring-framework/docs/5.3.1/reference/html/testing.html#integration-testing-annotations-junit4-timed
     * {@link org.springframework.test.annotation.Repeat}
     *  https://docs.spring.io/spring-framework/docs/5.3.1/reference/html/testing.html#integration-testing-annotations-junit4-repeat
     */
    @Test
    public void springJunit4TestingAnnotations() {

    }

    /**
     * TODO 待完善，这些注解只在 Junit 5 中可使用
     * {@link org.springframework.test.context.junit.jupiter.SpringJUnitConfig}
     * {@link org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig}
     * {@link org.springframework.test.context.TestConstructor}
     * {@link org.springframework.test.context.NestedTestConfiguration}
     * {@link org.springframework.test.context.junit.jupiter.EnabledIf}
     * {@link org.springframework.test.context.junit.jupiter.DisabledIf}
     */
    @Test
    public void springJunitJupiterTestingAnnotations() {

    }

}
