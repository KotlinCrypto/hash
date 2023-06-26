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

open class ParallelHash128XofUnitTest: XofUnitTest() {
    protected val B = 100
    override val xof: Updatable = ParallelHash128.xOf(null, B)

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
    final override fun givenXof_whenReset_thenReadReturnsExected() {
        super.givenXof_whenReset_thenReadReturnsExected()
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
        c7621831cc04198b9149c0572497ba39a3d7609408a728d9778a3363dded63b9
        86adb4b4e9bed4e20ef02b79829e6643564a5af54144e70e2bdf93a6310c49e6
        6a7ccd77c6d1f4b44bc3258ed9f405abe36b92aaf3a4d4b30ae9d4807057ee10
        ff25e4f351b65e8bb7b05de4084a7c008e58d0592662041f865e94e805ff3a22
        7f3c8f1457561da0f6cdada798dedcacdbabc592e79307bea11c4ac29a0376ba
        eb8b292c6263f98092a1dbc9bd78437a8c204c8a20f3638b5ea527c7271cd010
        edebf09f34a8826c8be53800bec8d8afac36f019046bb88c0a7f5737e5781a81
        8d78b79fd4175b6884bd9952d7c8ed9b78e5998363a899fb5a479a9277010284
        52e4f0042d165cfc7a8175fcb8fb7411aa0777b1b76fb5791591a4cebc25b485
        07eb87b22875ed74cc0052e1b499b4b208ebd321247c583dfb7cfa5ebf172298
        efc7673c5117a9fd1969b230d16f8e6910a5497710810818289e8783a3987f07
        3d4d7202b7e023ae8550f2e2e795ecd13ca942fba459b67579b902eea07e984c
        91fda399230cfa07eea125e18c9a338e2803745bfe909893211aff51d05bb048
        926cef8f242fc93257e3fd71593e8d6fb3c5829e870461ad6be97a2e67e6c1d8
        d974676d7ffe67a8893556830bd2f42ea2b16ab18cb430c14ecffd2579f6f2c1
        9663ae6c70d644ebb044d25a1dfe59812922bef659fab77541d27a6817b02487
        a2c96e2f8504f6f879761d91867e82db00308eb1b5df2479542aab1cc665c9e4
        ce0355e31127df13fc18c703cb5e6ef09ef12fe4f6e84b12645d6437fd5d588e
        1e584ee331317d8d4256bea3fa546eed0547cdab8ddaad2a07992c8857b436e0
        ebc2991b17b8e8b2760eb9d0a34919e484cb9c24730c1f31625804a278562b34
        85327057f51457dd8bdab35f68956e5a67f244795ee23087e9e285e3a2f10d35
        c9b8ddf47351cba9a975567875d2ec7f7fbe0b7b8651c29f8f4a8e953252d9c5
        85011f7253c41bfcedb9c3e9d51b02c36f0d7236cc1cb7e3d039a63cc552095c
        178b585ee46159fbf32875fb50c49767ab6aaa7a32b544aeea0ccd736f42b979
        441324cf1d9a4b19ea52636d9dd78cf3d2169f21ad697e405bc4a77f0f2c7caa
        86d10849315f54085ca9d390986c0b48db8db8d525d1e9579f9e221699f01148
        17a1ce2ae089932d8eb77b59b7feaadf5d0958d4b6439bb23156764d98a30bf1
        1c51cc8a94768c3ef7dd82815d88a16c51401a5fa6a7f086a8bbaa7458aa3bd8
        e6e98721a25a4ffb385a232d0e018cc0cdf3b72a02a85a545f38bff9a0e5a66a
        ddc8b02abaedc18a2a256ee133858034f3c3ab6fa021fb9a74b33ea1f96cc431
        37142b87749be4f46b0d53eb65bd83e55611ee0bd6ae1813768e99bdfbd85636
        abdf88f107c9d01f3bae7db55e93704e86feb5d4babe2f238a4c2b7274020971
        5cf4d6bbdc941c28258a60b98b04dab4bb6451a370db343932ccb6674a7253f5
        5d41213a1acc7749a37d44c34b46baab1f8c6c2dd5cdbbd7b4584f00b764091d
        a722adc46da9941076d6bb193478d3aad2be8a88250d1a1f3c1801105f4b3039
        a7dfd638e4182603c6e358e95ef5f89d133948784ca7fe9d5a449e5c05d1311a
        f5ed7188b5eb0b5f0f0eacc72864fb53b6e1875c757c9a61bb73e99c3b82bfa9
        ab7e3399bc73a822161e93be3ff2f74e551da2c3c1ce5e8377c6e159560b6f7c
        f237456177625d52dd
    """.trimIndent()
    final override val expectedPartialReadHash: String = """
        00000000000000000000bc9d4039091361b02ee01389287b71529d6eb12e84ac
        ef6cef1b295670ed011d7f7dd5aeb73aee5624d75a748a1f185afe6beec95042
        cfe334ab1a1ac7e8439c013ade457d0f518ecc5879297dfb1504c2d223dec5b3
        fec5430c468cc931bc260878bddf46a4961e2dd1c10c3d06dc3f37bfd5f13cc3
        e524db5947ea0865bf3cac48f960045f65a78577b3079589f46cdb26dcc6f287
        ce81ff5c33cef342dd3bdeeedd9b099f4f15efe792924d894a585990e8470000
        0000000000000000
    """.trimIndent()
    final override val expectedUpdateSmallHash: String = """
        bc9d4039091361b02ee01389287b71529d6eb12e84acef6cef1b295670ed011d
        7f7dd5aeb73aee5624d75a748a1f185afe6beec95042cfe334ab1a1ac7e8439c
        013ade457d0f518ecc5879297dfb1504c2d223dec5b3fec5430c468cc931bc26
        0878bddf46a4961e2dd1c10c3d06dc3f37bfd5f13cc3e524db5947ea0865bf3c
        ac48f960045f65a78577b3079589f46cdb26dcc6f287ce81ff5c33cef342dd3b
        deeedd9b099f4f15efe792924d894a585990e8476b881cd442c2a9038d0303d3
        14dcca4fe31abc9e5f687a38cd3a781887c1341d30366da4d4780661aff56ae0
        e52cb112ea68f7f88eb5637860fae3b03cd8dd8cfca6988392847c6ddf3cb7b3
        32c94500d56137f32a6cc193d6ff04a7a04fb4de841dfa433c44607373b9a194
        45c5d63d447e481b0260ecf6f39c4d58135e4f92129d57351db6568184cb234a
        aa5c5985f74ff886675f1354065e291ac8d3f2091ce799bf33a512611affda3f
        a9cb5d4780651d983e9bbe379ae3a35be687a340dc345ac816ac36f73b9101d9
        40075e472d276ad6b21817b2ad1f0c64e0faae1a84e3aedb6c60e99f5143b0a8
        c2256bde864014c588b56348efaca8f95662bcb86ef50bda09b194e6e22aebb7
        4a51cb66eadee15403181f1f37b77c65ce92da543ea4a90faf45db492c7bdc8a
        660aed28acb84e8701aa4a385e90a51b7ea27a614dc8a3ae6c61274f65e512fb
        df14f40330bd4e8d3793b4103381dada24c4adefd6e47489d0892d25aa32e7d4
        2f7d11c7d882ab3a193d659785bb2f807282ea21beedd8fa7b1a1f856a6c71b0
        08ed7535
    """.trimIndent()
    final override val expectedUpdateMediumHash: String = """
        299c28038c48937115fbec79483cb4e0f791f078de56d7dee68eeb1d694114f2
        7bdf79661db12d849af938f79143484a95f8606926e33eb6c3c1e9e9b5edb618
        216f511506be20ca7fe21bcf3a2047499c5032f0fbc4a32abc3378e3d8fd5b2c
        3b263eb4e2ddf175e5e584196931fb284218e3aefe33eaa08fbd3809b15b705a
        f2a852f9919eea8f66eff1f80f7ed18744de5293c5c928663b45dad604eceeda
        336d88943cf95644cebab42e8678259d0f6bfe0105a57f548f72f518ba404059
        b50be9f65163617154682617e8418ff71e31d489f9fcac18fd94780857bf2d0a
        63a074c4bd0ea4d4318ef66c58c7076c45f010991f2fd22372f0c4978fca9e0e
        c07018aae799f112e35c1306cf92067cc101ce4adfa24920b768e215820a6423
        456848034bffc7d36247aec634ccde563d2e7a526cd6acfda4148c21b206440e
        6f8f72b6f28917997a56fbb0b3d27cb653a984a59af3f1c5611477a1f010fbbd
        2a34e36466ba959aa64f8c54156bec6fb2350a86c71e554fae4fe9742cbbd468
        97fa2cbf601b256b5963aa33d60d0303735185222e00d5ec620363fad1cf6a8d
        97bd086d5e2336bf5ec298134cbca8516197cc5db1ae42b51f9380f227141374
        85bad4496de3cd83c385d47ac60605fccdaf90f77850f3cf1d83a48cd4129d3a
        23f85650603eae5dbc355fe576a25643448c33515d7bc6bd10c9b6f9211f010b
        de64b915bd6f954963e219a157e442f394b272043cdedb661409adbbe35f4250
        9d6ab54580635950678fc816beae6ace051c876afb105415b28f5b4302e542a9
        8890835e4fd545c1b388a4cfb41a3e5fb39dc41cd6647ab795814b8b3c05fe4c
        d7fccfe2a9d2009fd3420cbacd70e72a8b568f41e88811353e68cc2756c27e56
        7d0288ab9999e7762f295c8cb86b8ada58e3ca732aa5e9315b0597aa3de38f01
        a53a97a16614862a76ae68cd1c969fa1f8e3dd8a6fcad7a5813d842b6c4c307e
        981b59c0c62a7161060cf4ccd8dd2abd3ae01d407e54d0bc7600a07391cab11e
        677afd46a6875301f1ab6a990963b216d27b1a375a9b06789549e8141ef7034f
        f5467e3e6d235bcaff398c7beca64ba7c598c480e3ac66d6b1cfde1436030497
        f82fa21c3c9b2cd1dedac77fbdaa041bd279582d4914f52e70a7c01c7ee21824
        239945f954b0dd0e6940c9da5d5413c3a901f91645b6ca9b386e30c723fa1101
        de00c2ef2a1237fc55cfdf80189354a8ede7d741256334ab5af18febe27bc5a5
        bb9ca491ed04159f9b3a5a76d33c314ca2c1b2e6258e1397
    """.trimIndent()

}
