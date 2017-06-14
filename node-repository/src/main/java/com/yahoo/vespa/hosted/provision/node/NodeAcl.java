// Copyright 2017 Yahoo Holdings. Licensed under the terms of the Apache 2.0 license. See LICENSE in the project root.
package com.yahoo.vespa.hosted.provision.node;

import com.google.common.collect.ImmutableSet;
import com.yahoo.vespa.hosted.provision.Node;

import java.util.Set;

/**
 * A node ACL. The ACL contains the node which the ACL is valid for, and a set of nodes that the node should trust.
 *
 * @author mpolden
 */
public class NodeAcl {

    private final Node node;
    private final Set<Node> trustedNodes;

    public NodeAcl(Node node, Set<Node> trustedNodes) {
        this.node = node;
        this.trustedNodes = ImmutableSet.copyOf(trustedNodes);
    }

    public Node node() {
        return node;
    }

    public Set<Node> trustedNodes() {
        return trustedNodes;
    }
}
