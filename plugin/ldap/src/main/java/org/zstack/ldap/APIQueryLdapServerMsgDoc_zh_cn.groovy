package org.zstack.ldap

doc {
    title "在这里填写API标题"

    desc "在这里填写API描述"

    rest {
        request {
            url "GET /v1/ldap/servers"

            header (OAuth: 'the-session-uuid')

            clz APIQueryLdapServerMsg.class

            desc ""
            
		params APIQueryMessage.class
        }

        response {
            clz APIQueryLdapServerReply.class
        }
    }
}