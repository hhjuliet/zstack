package org.zstack.header.host

doc {
    title "在这里填写API标题"

    desc "在这里填写API描述"

    rest {
        request {
            url "PUT /v1/hosts/{uuid}/actions"

            header (OAuth: 'the-session-uuid')

            clz APIChangeHostStateMsg.class

            desc ""
            
			params {

				column {
					name "uuid"
					enclosedIn "changeHostState"
					desc "资源的UUID，唯一标示该资源"
					inUrl true
					type "String"
					optional false
					since "0.6"
					
				}
				column {
					name "stateEvent"
					enclosedIn "changeHostState"
					desc ""
					inUrl false
					type "String"
					optional false
					since "0.6"
					values ("enable","disable","maintain")
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
            clz APIChangeHostStateEvent.class
        }
    }
}