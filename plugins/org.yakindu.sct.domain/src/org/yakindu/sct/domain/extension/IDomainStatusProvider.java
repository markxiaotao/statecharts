/**
 * Copyright (c) 2016 committers of YAKINDU and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * 	committers of YAKINDU - initial API and implementation
 * 
 */
package org.yakindu.sct.domain.extension;

/**
 * @author andreas muelder - Initial contribution and API
 * 
 */
public interface IDomainStatusProvider {

	public DomainStatus getDomainStatus();

	public class DefaultDomainStatusProvider implements IDomainStatusProvider {

		@Override
		public DomainStatus getDomainStatus() {
			return DomainStatus.OK;
		}

	}
}
