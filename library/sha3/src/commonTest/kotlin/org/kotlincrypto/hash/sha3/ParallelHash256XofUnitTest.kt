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
package org.kotlincrypto.hash.sha3

import org.kotlincrypto.core.Updatable
import org.kotlincrypto.core.xof.Xof
import org.kotlincrypto.hash.XofUnitTest
import kotlin.test.Test

open class ParallelHash256XofUnitTest: XofUnitTest() {
    protected val B = 237
    override val xof: Updatable = ParallelHash256.xOf(null, B)

    override fun read(vararg args: ByteArray) {
        (xof as Xof<*>).use { args.forEach { read(it) } }
    }

    override fun partialRead(out: ByteArray, offset: Int, len: Int) {
        (xof as Xof<*>).use { read(out, offset, len) }
    }

    override fun reset() {
        (xof as Xof<*>).reset()
    }

    @Test
    final override fun givenXof_whenReset_thenReadReturnsExpected() {
        super.givenXof_whenReset_thenReadReturnsExpected()
    }

    @Test
    final override fun givenXof_whenPartialRead_thenReadReturnsExpected() {
        super.givenXof_whenPartialRead_thenReadReturnsExpected()
    }

    @Test
    final override fun givenXof_whenUpdatedSmall_thenReadReturnsExpected() {
        super.givenXof_whenUpdatedSmall_thenReadReturnsExpected()
    }

    @Test
    final override fun givenXof_whenUpdateMedium_thenReadReturnsExpected() {
        super.givenXof_whenUpdateMedium_thenReadReturnsExpected()
    }

    final override val expectedResetHash: String = """
        fa8be91f26c00d91c3c2f930ec4ec89488e32df18a1f0edb4fabaa68a8264793
        589116e7374dce9f4d405251e4fcf20a6c9adfb83456da5fbe85964078b3d259
        b1f68ac8392c3789b6e3d5c4382379317caa0f1c6057ff5e12abfa50d50b4b57
        9f99fba1466526486d7c5b3c5348d9ba068672d9eb92e12fee2d0b4a7540acf0
        975d1e069aea1afb36bdf9dc161560dc2a896e116cd520c4d52289fe2e73924b
        38fcf216429d710cfb4255e30aa41a2ee44941483c0fa4bbc889040a90fd5d26
        0d46cceac27cff8edcd0da25ccacfd37cff739dc24de706af6cbba60212274ba
        4d937d8e51c205cff8047fd88729a0d0780d9d46dd39175e01ee72ab3a6718cb
        e8644cb0f4266058eeb6a1bc3d1133a48801ea73be7c74ade5cef912996ebaff
        3c7e94613b3c082161a6cac73c6cac619fcf5e2fc24b03d626d723eda04d7491
        bab8d7afcc331be1817cbda5c040b0b6a1415de617228c024d00bb37c47d1a90
        b4d108b358123abdda3c0e95b9e8a8bbd3688e881d441976c32d13e2e7729722
        e8fad24e53f19b4f8089bbfa2a0f54524510f4d41f949cff560bc14615537dbe
        cef2c3e681eb109ef68b345b77e1ace1fe23c150ee1876a9442ac3bf6e88659b
        b166e99b15ce0406624a8dfa43170725fa6000ec1bf052ad3dbfdb6561433fda
        cf2319486af8b3cb53184f889ed9cb458c09de44adbe5e40925f3e4e9fbb6635
        3592506af02fb5921d64d83bdcf12cd36fef63dab8fccf57015237649d8f0aeb
        10a12cc578ee20694e5871bd92549e27777048da1ce9219592ec8e45c1e201f3
        205f206cbcd7a8808f43fcdd0d3ad45a1dfadd96b22589b475d101c51b685e61
        669a783bda2232883bf3b44990df4545556a08b4c5ff0055e71c0f748e0fe0bf
        c84e10a50f1875eb057a4fddfade0c86380d9fdacd656191c7e00604f9e7202d
        78cbfbe08d8813ec3d9931290deaeb8aeb0f9c733d4b599430cb162d181f3300
        c50c37f6aeb3d310df336bb1a29b8e072cabb28a4c843bb8e2e4ae27cf0dd8bc
        792c0f8a4f7e16a5ee549f1d89c0ab7998e9b54c1da4b65e082c1b25763f07f8
        dcb91dee894a32f7a6a0fd9f6f942bc1afbb78ae6e8330c223d8ee6d4dad40a1
        b93ea248e0b54f058e01cea42702d9bb3b9f57cfcaeccabafa5e985230eb9b58
        29e666d8705766f5dd8500b2e3ffeee2e607e4b61d89a9e5d91fce303a05a3c8
        b7a717e601f08f6bf9d8ef1b699f29f96e13d9b7f612ebab46bb37f2b873da8a
        9b4e829b621ca3bb876109962aa51757e6dd014e500537e5aff3a4affe03597b
        639361c29515cd573af9871cd1da4d60990a4110686118881c21b6aa52518971
        a0c90796e9b6cd0c8a9d87c20ebfb83c14a3b72573ae8b0d30e6080baca7a5cd
        7bfc35350dc4276b8e3faa10e46329273a54cfa99c5d746d0fbc4790346c5091
        ed46bddb084fcf2ddf1b491da0f2424d5c598718894b8737b83651c5ee3dda8e
        02dfec471ac691d49d5280101b0175f969b0301eb003c86951b5163b332476fd
        7675304282dd22c8a010ecfda037bbc2e5b1aac0649bd4ea5cbe23ae1cba1e55
        6502975cf8b52300d6798d987d11af690d20a34f0c38c44eca78f061dd3bfada
        fea1bd5bb1c176e108beab582daae571ec26fb891b022b29a74b222aab50641b
        43bcd6ca49d6f8f27f45bf2cf7ce30410c6753bc18737b95029f40973221a171
        84270b25b3a5affb25
    """.trimIndent()
    final override val expectedPartialReadHash: String = """
        00000000000000000000d8fba9a826cbb771d747cee9e02c43cd8e5409fe7ba6
        873ddde069d3426dc0b28658617f20b4484e4b1b5621f376639d99c781dd49ad
        a212e1f766bdf431f4d40408e19074f78cd82373e3678af00f5c84ac754f8d2a
        fdb97aa994a7955067cddb5dcaee88e5806012ac425a42d971d7fab6e0bfa7cf
        a5c6ee585f49420adb5b05b927f56b667d74e96570014e910f9e3b9c3d6bed8d
        52b03694d24306161acbcb35a7451685def96d19c039534d5cc3d58b93cd0000
        0000000000000000
    """.trimIndent()
    final override val expectedUpdateSmallHash: String = """
        d8fba9a826cbb771d747cee9e02c43cd8e5409fe7ba6873ddde069d3426dc0b2
        8658617f20b4484e4b1b5621f376639d99c781dd49ada212e1f766bdf431f4d4
        0408e19074f78cd82373e3678af00f5c84ac754f8d2afdb97aa994a7955067cd
        db5dcaee88e5806012ac425a42d971d7fab6e0bfa7cfa5c6ee585f49420adb5b
        05b927f56b667d74e96570014e910f9e3b9c3d6bed8d52b03694d24306161acb
        cb35a7451685def96d19c039534d5cc3d58b93cd073f77566cd0152563169768
        5a67be518f25222e46336a37fcce9317b6ff915392a929cb5dcc15eec230458a
        c375826206b58b059b0173b5c4b9c1ad2ec3252ef0b7c15ec95a6c50a8e4a474
        c37aaffc778366e9bd5bf42002e0ddbc04c26beadca5e4430e64f2e8059f0f05
        b1eb1e0e1250ebdd2f8bea3df0367c7993215c65023e7bd6186da68f153a71b5
        8356747af9b083ed196fe270bc50e40927d00acea6f22d9f139160bb305b1bdf
        74e2217f8740628e503da01ac732a437d7e27f8317171b15da8cad71a884ac59
        a56ba9d2f48976b3eed702438346a7653b15ff5a796d799527869d540b3b13f9
        47a14d8c770b77ceb0bb3c26df008ce5cb8ca7767d1519a37c1ecdadae61e80b
        e7c98de729aa5d91f6e8d8b4de2104a69bf92803d0b11ae5e9ab37cf433656bc
        7ec0485b1114b89dd7c41290f64379551b99fb00c9b7e682bb712581db283758
        3351a538c29e7aed360e895894fed02f50ac239c7c6c49b332fcb631dc08bff8
        8f67e1d7dd16133fb66dc99c68af2ab112fdae9421d049b4c02b607f29d26275
        da56684d
    """.trimIndent()
    final override val expectedUpdateMediumHash: String = """
        b1f562b857bc609d32776768696b4fdeb9b05eaa31d23695485116a4fbf07700
        19cfac575b4a703883dcfbe13488fd4b513fb196265386d6dd359e3e56b58ec0
        a0de720d07badb809927e68d4542731ec4c1ce6de652c95b875cfd995299b66e
        6e30f2c86c73cddf1d930f5e0c79bd27200f19777e430af2ae9ca3b498346464
        6c3c9f77da5acee85aa422836d110a63345a646f9b5c40f1b82fbb1a32ba6e16
        9f24c2402af4fecab9dfe0577decabe1f4ecf1572ff23e6380ea55030cb29e57
        6bcb9b03d603b73a6ba87d9380b81c162c1f679c6c223612bc7a0d2a2ad22b78
        e5f4fb5550ac0b5e419ae1b833879cfe99e32c13192d983f83812eb960a97e83
        e1ab2c54540688e2f80c659eff1561b70106201f0ba278d5e229a44a276ca83f
        6c3add3f82c187a36ff726be2a61534c7eccff51c0eeab581ea82cf89113a1fa
        f1bb74673a3be40c97c857511265c95c46d2cdb78db1eae16607470daaa8eadc
        1a2bf396bb9de9aec5b6b95f5800f327dfa23b7ec1b85127471a3b8607c7a880
        ffb6703c6309edc1d0583f302a82e839c703d5212c8db337283e00cb45b037bc
        ae343955c28129d3157f42f0327dea8e28bba03f59de3f58dc359f22e58bae08
        627bb4f9391ad2a0b9eac9a0f0e419a446aab50d740cb8d145a56161d5624054
        22f1a527dc478e3b1178b196fb85ad87017f7d455ecbe62c258204fa10dd5590
        9460469f052b0152f422fb8efae5cbf5ef1ec8f162c30dc7f77f188fe6da123f
        c140b0233f49480284cdcdb8897c3918218a5f599363c5c1a4c145f23d164b1d
        3118f40d7e002f1b6ae68a08e0c8bdcbedca6c3628ead153b32b7ec497e291ef
        309e9e1e98c367a7b646c1f1e5511d65bb1f30796bec64f4e3a2e07ec93f8a04
        2321d6cc5bd76ed028e653545921c93a68e474f0ce514385066b4e9efa62385c
        c4b33551e305138a2dab705663e3c2639b2c25c417c5ec822bded88ae09a79e9
        37e880650ed309dfc5b718e8140107afaac2c45d34d6969c29c8c5b3ae4c4b23
        f015a02eae3a495c5400bf5ce1b8b9a80df66edd71f8cb28beb1b93ce658e024
        e38cb95ef7d94be7f81156ebbc54b26e2c16fdf5af0adee2ae8e54a152f72659
        11d996f39b78d00f9561f9eea35c7c82bbf2511012b90280921d460de65536d2
        d56a86667ede1242380a461011cc63438131708ef2c09a7bde2139b3274712b5
        345a7000e044989fb2a2856e50d316e3a618b751138a14283ef8e771c6db5387
        6204dc099f8237fbb09b20860e6b7f1f462425e23f617df1
    """.trimIndent()

}
