/*
 * Copyright (c) 2023 Matthew Nelson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/
package org.kotlincrypto.core

/**
 * Factory class for implementors of Digest or Mac to wrap them
 * in [XofDelegate] such that there is a distinct separation of
 * functionality between those class types and [Xof]s, while
 * still being able to share similarities/capabilities.
 *
 * e.g.
 *
 *     class SHAKE128: SHAKEDigest {
 *
 *         // ...
 *
 *         @OptIn(InternalKotlinCryptoApi::class)
 *         companion object: XofFactory<SHAKE128>() {
 *             @JvmStatic
 *             fun xOf(): Xof<SHAKE128> = object : XofDelegate(SHAKE128()) {
 *                 // ...
 *             }
 *         }
 *     }
 *
 * @see [XofDelegate]
 * */
public abstract class XofFactory<A: Algorithm>
@InternalKotlinCryptoApi
public constructor()
{

    /**
     * Wrapper for a Digest, Mac, etc. to act as a delegate to
     * the [Xof].
     *
     * @throws [ClassCastException] if [delegate] is:
     *   - Not an instance of [Resettable]
     *   - Not an instance of [Updatable]
     * @throws [IllegalArgumentException] if [delegate] is:
     *   - Not an instance of [Copyable]
     *   - An instance of [Xof]
     * */
    protected abstract inner class XofDelegate
    @Throws(ClassCastException::class, IllegalArgumentException::class)
    protected constructor(
        protected val delegate: A
    ) : Xof<A>(),
        Algorithm by delegate,
        Resettable by delegate as Resettable,
        Updatable by delegate as Updatable
    {

        init {
            require(delegate is Copyable<*>) { "delegate must be Copyable" }
            require(delegate !is Xof<*>) { "delegate cannot be an instance of Xof" }
        }

        protected final override fun newReader(): Reader {
            @Suppress("UNCHECKED_CAST")
            return newReader((delegate as Copyable<*>).copy() as A)
        }

        /**
         * Returns a new [Xof.Reader] for the snapshot (i.e. copy) of [delegate]
         * in its current state.
         * */
        protected abstract fun newReader(delegateCopy: A): Reader

        public final override fun equals(other: Any?): Boolean {
            return other is XofFactory<*>.XofDelegate && other.delegate == delegate
        }
        public final override fun hashCode(): Int = delegate.hashCode()
    }
}
