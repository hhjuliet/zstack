package org.zstack.testlib

import org.zstack.sdk.L3NetworkInventory

class L3NetworkSpec implements Spec, HasSession {
    String name
    String description

    L3NetworkInventory inventory

    SpecID create(String uuid, String sessionId) {
        inventory = createL3Network {
            delegate.resourceUuid = uuid
            delegate.name = name
            delegate.description = description
            delegate.sessionId = sessionId
            delegate.userTags = userTags
            delegate.systemTags = systemTags
            delegate.l2NetworkUuid = (parent as L2NetworkSpec).inventory.uuid
        }

        return id(name, inventory.uuid)
    }
}
