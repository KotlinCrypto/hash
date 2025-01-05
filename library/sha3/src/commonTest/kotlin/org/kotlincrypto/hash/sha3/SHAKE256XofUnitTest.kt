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

open class SHAKE256XofUnitTest: XofUnitTest() {
    override val xof: Updatable = SHAKE256.xOf()

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
        46b9dd2b0ba88d13233b3feb743eeb243fcd52ea62b81b82b50c27646ed5762f
        d75dc4ddd8c0f200cb05019d67b592f6fc821c49479ab48640292eacb3b7c4be
        141e96616fb13957692cc7edd0b45ae3dc07223c8e92937bef84bc0eab862853
        349ec75546f58fb7c2775c38462c5010d846c185c15111e595522a6bcd16cf86
        f3d122109e3b1fdd943b6aec468a2d621a7c06c6a957c62b54dafc3be87567d6
        77231395f6147293b68ceab7a9e0c58d864e8efde4e1b9a46cbe854713672f5c
        aaae314ed9083dab4b099f8e300f01b8650f1f4b1d8fcf3f3cb53fb8e9eb2ea2
        03bdc970f50ae55428a91f7f53ac266b28419c3778a15fd248d339ede785fb7f
        5a1aaa96d313eacc890936c173cdcd0fab882c45755feb3aed96d477ff96390b
        f9a66d1368b208e21f7c10d04a3dbd4e360633e5db4b602601c14cea737db3dc
        f722632cc77851cbdde2aaf0a33a07b373445df490cc8fc1e4160ff118378f11
        f0477de055a81a9eda57a4a2cfb0c83929d310912f729ec6cfa36c6ac6a75837
        143045d791cc85eff5b21932f23861bcf23a52b5da67eaf7baae0f5fb1369db7
        8f3ac45f8c4ac5671d85735cdddb09d2b1e34a1fc066ff4a162cb263d6541274
        ae2fcc865f618abe27c124cd8b074ccd516301b91875824d09958f341ef274bd
        ab0bae316339894304e35877b0c28a9b1fd166c796b9cc258a064a8f57e27f2a
        5b8d548a728c9444ecb879adc19de0c1b8587de3e73e15d3ce2db7c9fa7b58ff
        c0e87251773faf3e8f3e3cf1d4dfa723afd4da9097cb3c866acbefab2c4e85e1
        918990ff93e0656b5f75b08729c60e6a9d7352b9efd2e33e3d1ba6e6d89edfa6
        71266ece6be7bb5ac948b737e41590abe138ce1869c08680162f08863d174e77
        e07a9ddb33b57de04c443a5bd77c42036871aae7893362b27015b84b4139f0e3
        13579b4ef5f6b6426563d7195b8c5b84736b14266160342c4093f8abea48371b
        a94cc06dcb6b8a8e7bce6354f9babc949a5f18f8c9f0aaefe0b8becad386f078
        ca41cacf2e3d17f4ec21fed0e3b682435ad5b665c25d7b61b379e86824c2b22d
        5a54835f8b04d4c0b29667baeb0c3258809ee698dbc03536a1c936c811f6e6f6
        9210f5632080064923fdf9cf405301e45a3f96e3f57c55c4e0b538efe8942f6b
        601ac49ea635f70e4ba39e5fce513cfb672945bb92e17f7d222eab2aa29be89f
        c3ff24bc6b6d7a3d307ce7b1731e7df59690d0530d7f2f5bb9ed37d180169a6c
        1bb022252ab8cc6860e3cf1f1414c90a19350b526e3741e500717769cdd09d26
        8cc3f88b5d521c70aa8bbe631fbf08905a0a833d2005830717adba3233dd591b
        c505c7b13a9d5672ad4be10c744ac33d9e92a23bdee6e14d470ee7dc142fe4ef
        f4182a49beeec8e4b1fdaff1bec8a4267aec571011272f0c36555077c073a184
        e5b69820f36d7e0d4d6c37a501ee3ce2c65b9cc5ab6d37f0a8b01231febc7458
        3a165bdce910a1dab2bf35547a8878266636f2a856312edeb9c4c1f0c14f2ddc
        f1a5415b8d83b7108fb7efdcc134d8b07de479e6e60cf9369069b276ab3b16f6
        d4565cd893c6bd78689eaed282d143e9e47db195113aa13774eeb76c52c4bde9
        0242cc76f6f472a65f55e5f3f9a4e6490b988f7aa4383b66c20fe7427ba640ab
        3ea82031e435972f50683663f5f8ddf75a9c9b8fe6d050a8487fc89cfd63df9c
        91c2541c1cf5a1e1f7
    """.trimIndent()
    final override val expectedPartialReadHash: String = """
        00000000000000000000030f4a342728ddc61799a29afb8f904a3276e04ead3c
        3fb0f3ef2fb2ec9c85a3cd87c6549a17c727c3d544def386d005751a06cc2a96
        c489b48ce270762e7794e808924cea4f5e1a3bd272caec6c650d17c5b438ca0f
        2e4b966b1f8faf56a42f496290b4d01165997ba80236e3a4fd9b1d13c72dfe82
        86fcaa16fefb53a5b5c03a73bc1afd988d3f7459c0582f42b2a5c79836182876
        6b22804ad2e67ecf721edebdcceac9ae52065250c7c3ecbb006ac20bfd4c0000
        0000000000000000
    """.trimIndent()
    final override val expectedUpdateSmallHash: String = """
        030f4a342728ddc61799a29afb8f904a3276e04ead3c3fb0f3ef2fb2ec9c85a3
        cd87c6549a17c727c3d544def386d005751a06cc2a96c489b48ce270762e7794
        e808924cea4f5e1a3bd272caec6c650d17c5b438ca0f2e4b966b1f8faf56a42f
        496290b4d01165997ba80236e3a4fd9b1d13c72dfe8286fcaa16fefb53a5b5c0
        3a73bc1afd988d3f7459c0582f42b2a5c798361828766b22804ad2e67ecf721e
        debdcceac9ae52065250c7c3ecbb006ac20bfd4c803b7575c08239f74bb1a650
        5904fc57bd910c5c5238f5d090849a98bea32df9db91fd068245b6ca77d438e6
        9e2de758bc434eed75efc7c7d278fddcfef7351f49b85691cb51e75cd9629471
        674a6a9940ca5c9231781b4b23a2e23c454d95dab17a0c59131000a60937244a
        7357f64adfac43649b155b1b73fa60cb1d08e7279f1786d69813ae6c9cc2682b
        e65417e7dc16915613f772c56fb8e2c1190cec4d02aa4eb0979482f01ae7709f
        a5211be94993ae7e1f587d1585a7c5d7bfd39a3b091e1f477785dbf2096364ec
        2f98820931426d85cedfdbff1dad5aa7f98f0ca3162da5fa9e085853525c3ba3
        4a53a6b55b7ff33f03f74ba21a98a6e5c41a70036dfbe642c772c041097ef54f
        7ae71d891f68448584d64141884ca1bc569cb53afbdbc21b6460b397066150a9
        f1128b99a860e025786bd1e932fd48966ef61e1e27c8309ba7212fd364a29617
        c65c1acc0b488425eb924c43ae0a884edca7531953db40be7739d9f6f93c2736
        dfe10bcbbfe237fa0599aa4c680fd0f50d90a33fceda9c14d3d47707afa0a2ac
        c51ad630
    """.trimIndent()
    final override val expectedUpdateMediumHash: String = """
        42dacceebb007046252e70a319ab2e162cd0ffb3b3e6fcf0e865fbf3763fa732
        5d4add48be7917c2bfb09e6fa0a197cbece5a1ee5b7e1a554b514197e84b7f46
        56dd7b69ef992ab20ef8fd1345d99e6c6c1add27053508028532045b62f34d2e
        e527706924df561b953662031ffc3c0c5d7bfa552272f5125151d93594466c27
        f42fb8d50071cc7d3897c095535a1fcb98d9acf57a16f3347fa5807c0ac1260e
        7cc9fc5d89b3c8c20f7ed6c50eda4e40700ba339ade02dd630e616cecc0c2cc2
        28bd3ebe4d242d157dc8167077a5fa7680a603c0d87166e35fdd18210f013787
        d5ca01855ddfca1ba22ba461afb49991bb6385aa9db0f08bf933f41c3cb84071
        05dff8f11470318bf115c8a6921bdfbc96f9605d86c637c62171129c56e6ac1f
        2abb75a8d12f06a034925d979effc99197d4ec46fecbe01062be8c9ecd8c727a
        b91072754d48c89bd3f046377e6642900bea10368e37a77333eb8f63a0235bb2
        e133e5db4162a4476525c92b34324e920ce5355c506b481ae883926f971af114
        a6fa4a4ce2b853fd8f32f727f1fd89e1c44b1206acfedbe624ce49e665e96520
        928de377a4c9837c0d975ab00e95b75012d0f3ad6a5b7b7fe66b3f56b98dec30
        c71d4eec8a52b3e53bbcfda0b871ee1e1591bebe4e3d9fb3b81ad0a6698b924b
        90b1867f9c982eb93adebe48e2cd71170431e7c4083ee5d04709ecab8ad35026
        d5b76c937275242cefeb7592fa4005281d4250687fc6e003f8557054941dc0cf
        d771601721655f0580c047dd608ea274f1b7d5779a4c70b11dd35e171db43f35
        ae3e5d49b94f184c49bc02813112a1d54ecc4674091a2023782889ef6a442954
        3de63efbbfa884e07e0d24a0dde6e90bcbd847dd22eeb83d953828fcea934dd3
        4c1058c67a9f5305c3cda7d4a776c35d424c38ced1eae30d5b0f522b0778e534
        ed34a71fbf13aed66b8a60bba0cea66ee883a0700a4dc8bb1558debcec5f31a1
        f48b702d5c312f54005b8d1c76ae5695fb6ef0afeffcbc0e86e91eab635c8aa8
        0dc8670165f8c2c00a4caf4e9286f111011e4668103c19bb5eaac3a58b35ce99
        034978ca293cf7833bb350a2a214686df1a14dc00f7ffe1ac2dc26c64684e075
        265b5d687586ab7d352c7c4c86aadd89cfff557190ecadb21a6f5921b5c620f7
        4b4a35e9a75881b73ed8029d6c9f6ce7e6ccdf779840229a4015a4844b6c67d1
        9a1489694c58e7e4475176925e29de5fa7e2cec528b93872ab2232b46d61aa71
        8866e219dc603cd5c773d88734db3192d286e7a567f4ff32
    """.trimIndent()

}
