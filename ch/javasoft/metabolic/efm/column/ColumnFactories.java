/*
 * =============================================================================
 * Simplified BSD License, see http://www.opensource.org/licenses/
 * -----------------------------------------------------------------------------
 * Copyright (c) 2008-2009, Marco Terzer, Zurich, Switzerland
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 *     * Redistributions of source code must retain the above copyright notice, 
 *       this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright 
 *       notice, this list of conditions and the following disclaimer in the 
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Swiss Federal Institute of Technology Zurich 
 *       nor the names of its contributors may be used to endorse or promote 
 *       products derived from this software without specific prior written 
 *       permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE.
 * =============================================================================
 */
package ch.javasoft.metabolic.efm.column;

import java.util.HashMap;
import java.util.Map;

import ch.javasoft.math.BigFraction;

public class ColumnFactories {
	
	private static ColumnFactories sInstance;
	
	private final Map<Class<? extends Number>, ColumnFactory> factories = new HashMap<Class<? extends Number>, ColumnFactory>();
	
	public ColumnFactories() {
		for (final ColumnFactory fac : getDefaultFactories()) {
			factories.put(fac.numberClass(), fac);
		}
	}
	
	public static ColumnFactories instance() {
		if (sInstance == null) {
			sInstance = new ColumnFactories();
		}
		return sInstance;
	}
	public Column createBinaryColumn(Class<? extends Number> numberClass, int booleanSize) {
		final ColumnFactory fac = factories.get(numberClass);
		if (fac == null) throw new IllegalArgumentException("no column factory for number type " + numberClass.getName());
		return fac.createBinaryColumn(booleanSize);
	}
	
	protected ColumnFactory[] getDefaultFactories() {
		return new ColumnFactory[] {
			new ColumnFactory() {
				@Override
				public Class<? extends Number> numberClass() {
					return Double.class;
				}
				@Override
				public Column createBinaryColumn(int booleanSize) {
					return new DoubleColumn(booleanSize);
				}
			},
			new ColumnFactory() {
				@Override
				public Class<? extends Number> numberClass() {
					return BigFraction.class;
				}
				@Override
				public Column createBinaryColumn(int booleanSize) {
					return new FractionalColumn(booleanSize);
				}
			}
		};
	}

}