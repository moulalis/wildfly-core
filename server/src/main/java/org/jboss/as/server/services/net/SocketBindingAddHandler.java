/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */
package org.jboss.as.server.services.net;


import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.ADD;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.CLIENT_MAPPINGS;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.FIXED_PORT;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.INTERFACE;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.MULTICAST_ADDRESS;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.MULTICAST_PORT;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.NAME;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.PORT;

import org.jboss.as.controller.AbstractAddStepHandler;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.controller.PathAddress;
import org.jboss.as.controller.operations.common.Util;
import org.jboss.as.controller.registry.Resource;
import org.jboss.as.controller.resource.AbstractSocketBindingResourceDefinition;
import org.jboss.dmr.ModelNode;

/**
 * Handler for the socket-binding resource's add operation.
 *
 * @author Brian Stansberry (c) 2011 Red Hat Inc.
 */
public class SocketBindingAddHandler extends AbstractAddStepHandler {

    public static final String OPERATION_NAME = ADD;

    public static ModelNode getOperation(PathAddress address, ModelNode socketBinding) {
        ModelNode op = Util.createAddOperation(address);
        if (socketBinding.get(INTERFACE).isDefined()) {
            op.get(INTERFACE).set(socketBinding.get(INTERFACE));
        }
        if (socketBinding.hasDefined(PORT)) {
            op.get(PORT).set(socketBinding.get(PORT));
        }
        if (socketBinding.hasDefined(FIXED_PORT)) {
            op.get(FIXED_PORT).set(socketBinding.get(FIXED_PORT));
        }
        if (socketBinding.hasDefined(MULTICAST_ADDRESS)) {
            op.get(MULTICAST_ADDRESS).set(socketBinding.get(MULTICAST_ADDRESS));
        }
        if (socketBinding.hasDefined(MULTICAST_PORT)) {
            op.get(MULTICAST_PORT).set(socketBinding.get(MULTICAST_PORT));
        }
        if (socketBinding.hasDefined(CLIENT_MAPPINGS)) {
            op.get(CLIENT_MAPPINGS).set(socketBinding.get(CLIENT_MAPPINGS));
        }
        return op;
    }

    public static final SocketBindingAddHandler INSTANCE = new SocketBindingAddHandler();

    /**
     * Create the SocketBindingAddHandler
     */
    protected SocketBindingAddHandler() {
        super(SocketBindingResourceDefinition.SOCKET_BINDING_CAPABILITY,
                AbstractSocketBindingResourceDefinition.INTERFACE,
                AbstractSocketBindingResourceDefinition.PORT,
                AbstractSocketBindingResourceDefinition.FIXED_PORT,
                AbstractSocketBindingResourceDefinition.MULTICAST_ADDRESS,
                AbstractSocketBindingResourceDefinition.MULTICAST_PORT,
                AbstractSocketBindingResourceDefinition.CLIENT_MAPPINGS);
    }

    @Override
    protected void populateModel(OperationContext context, ModelNode operation, Resource resource) throws OperationFailedException {

        ModelNode model = resource.getModel();
        model.get(NAME).set(context.getCurrentAddressValue());

        super.populateModel(context, operation, resource);
    }
}
