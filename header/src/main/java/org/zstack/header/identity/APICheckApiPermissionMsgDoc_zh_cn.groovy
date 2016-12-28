package org.zstack.header.identity

doc {
    title "在这里填写API标题"

    desc "在这里填写API描述"

    rest {
        request {
            url "PUT /v1/accounts/permissions/actions"

            header (OAuth: 'the-session-uuid')

            clz APICheckApiPermissionMsg.class

            desc ""
            
			params {

				column {
					name "userUuid"
					enclosedIn "checkApiPermission"
					desc "用户UUID"
					inUrl false
					type "String"
					optional true
					since "0.6"
					
				}
				column {
					name "apiNames"
					enclosedIn "checkApiPermission"
					desc ""
					inUrl false
					type "List"
					optional false
					since "0.6"
					
				}
				column {
					name "systemTags"
					enclosedIn ""
					desc ""
					inUrl false
					type "List"
					optional true
					since "0.6"
					
				}
				column {
					name "userTags"
					enclosedIn ""
					desc ""
					inUrl false
					type "List"
					optional true
					since "0.6"
					
				}
			}
        }

        response {
            clz APICheckApiPermissionReply.class
        }
    }
}