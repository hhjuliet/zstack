package org.zstack.header.vm

doc {
    title "在这里填写API标题"

    desc "在这里填写API描述"

    rest {
        request {
            url "PUT /v1/vm-instances/{uuid}/actions"

            header (OAuth: 'the-session-uuid')

            clz APISetVmConsolePasswordMsg.class

            desc ""
            
			params {

				column {
					name "uuid"
					enclosedIn "setVmConsolePassword"
					desc "资源的UUID，唯一标示该资源"
					inUrl true
					type "String"
					optional false
					since "0.6"
					
				}
				column {
					name "consolePassword"
					enclosedIn "setVmConsolePassword"
					desc ""
					inUrl false
					type "String"
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
            clz APISetVmConsolePasswordEvent.class
        }
    }
}