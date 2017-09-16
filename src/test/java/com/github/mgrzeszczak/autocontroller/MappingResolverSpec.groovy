package com.github.mgrzeszczak.autocontroller

import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class MappingResolverSpec extends Specification {

    def "should resolve path(#className, #methodName, #expectedPath)"() {
        expect:
            MappingResolver.resolvePath(className, methodName) == expectedPath
        where:
            className            | methodName || expectedPath
            "UserService"        | "query"    || "user/query"
            "UserService"        | "getName"  || "user/name"
            "UserAccountService" | "register" || "user-account/register"
            "UserManager"        | "delete"   || "user-manager/delete"
    }

}
