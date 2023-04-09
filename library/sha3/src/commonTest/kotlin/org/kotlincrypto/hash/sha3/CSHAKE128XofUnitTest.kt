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
import org.kotlincrypto.core.Xof
import org.kotlincrypto.hash.XofUnitTest
import kotlin.test.Test

open class CSHAKE128XofUnitTest: XofUnitTest() {
    val N = "SOMETHING".encodeToByteArray()
    override val xof: Updatable = CSHAKE128.xOf(N, null)

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
        a310f48745f5546f73dcbbd2b26e02c4242628f447530566cafe58e3dcc2786d
        b082cd8394a4c882c24c3a17cdb530828e12152acfadbef9e92e8b1ce11c990f
        74c19046fa4f1959e38bd4d756226eee192d448f0c0c6d321e8b496e761b5787
        167759efbc7b5202fdf0d7684b73483297712ced6451a4ef3ab51ae64b6b3d47
        fdba6c29ddc59769d2745d58afd5200b3384edfaefe33f9f3cf434a23432da7a
        8831c7237e6809c2c1b00d9361994873ffc6a0003e181b9aba8ea790c77a15a4
        53c9d24b814141a165d1343ab54571cbf8acfa5be64175568c5d7b18962ced02
        150e42b8fb3bb3eafa094bf49623dbf3abae16424fc766bb1f523fda5b3e2e2e
        3df4db0135820cfb3c42a824e134351b2fcf05ff23196528ad6b6d8feb877c83
        2d8736ec2c7e8eb44abd607c34e17911a3711f087814f0650d8757742cbfac34
        be4617a3c97ca94e33f1855f6bfc3abbbe22473d4c4bc4828d12e1e24ed76aee
        11e7d6c6a668af80d261a790eda2ef822fbd2129bed78c65e72562b8bb49d696
        3c00f4861cddcfccf22f8f254900f928def07404288548cddb3d819f50b1c4bc
        821a30a137730f90a6b345804e51f614554d6a6966d5e9698d052cc11a13895d
        2e50185e1c7210ff559c924798caa24eacb6acfafd3d4927a1226a109def4fc6
        dda53c4244be71daac305b3709618f77e6762b0f277c980e3f8a16a9b17f1e89
        dac6d9fb2f27fd7bdca45db69ae7dcf98a9ec55c904d603dcf9215c443500028
        18ed98358c445ddd1a57b3a8b95a4e66651e7bdeb89c3ab610bb34ca828e91be
        c4c9caea164d70fbf2a58643388efcfdb9379953619ac5c78fbbd7fb224a57f7
        931afbe63ec75db90586d7a2e85cf5def4d3dccc3ab1692d9a75406bba39b471
        5ba8ac5f588d4f3b81ec0957d537fd448acac242e12809e5405f8691d0c631e6
        f719daa25473a8848f19a861fca98641111118699f91e746256607f2402e0e83
        f26dface4ffc50f4385bb29527b7323a123ad42c6bc99848b597db39c1d08791
        17d34e796086f5cc31cc1b8faa6740e2f8518156175ab188d34cadbb1590aed6
        39695d3d5d09ca6903b854f83172677d0c3ee243aa4fd477402042bf87d6323e
        7140002e8baeea98cc5612813d0042ab935f4b0b9867f9470ba00a10b915f8bb
        7fa2e9bf480cc24fbbf8e61481db5347b02d32e31c283355458543e76ec9e6c3
        31f5c07608aee1fe45f579d7151df7eaeb0db5effb327ee4ef1b7620b8fde4ed
        abf7138c9135125f4c348c10ff2af4e865a658de2ea50a5064e777c7dee4fb17
        7bd23e57e3b687c10e4dbbcd17a712eb3be6402431254e5c3b405a9be03b56ba
        a2cfa70d7cbe6b023338e85e53f1c2fe4dded61655b8ad093e1b6ed362be1008
        2b2e353fae42c2fb5da46679af00487989b02ee79fadcf7bd32ae3880b250aad
        56fbc529e08279b63836785560efe7422f8f516acef7204da5eabeba05667af9
        adbdc379e40eec5c9884cb8ae9526cff190f682be112c2308c4b0950a32b6b8f
        8da3ffabddc0fa790bc3971ab60c99749ec3aed3abc0ac4df7fb6677b8e300db
        261b3b7ce6c13c78bdb9752e34545ca4a7cf99f61186f865fc9dddb543f2f10f
        7296e7de7a6fd72280350500e2eddd509328e18c2b79a60241a784ecbcc0cd78
        959712abe84ff06af560725e3a8c3ab18c65418d0e270a50f7b49d37fa8a38f6
        006ac409419c8129bd
    """.trimIndent()
    final override val expectedPartialReadHash: String = """
        00000000000000000000f4161585d94be8b4ba5f9e6d4814361c42fdcfbb6054
        77f547f73a8fa6f83da8c5c0dc522aec47196ed32ae2509edd597c712f3eef0b
        3f1a07efc2c21e0101322924b3b4535f4295c4df55cc98c1243153431af59297
        8c5b27df0058009256d2d80e3429a2d95c44b96cea60b243e583d8e95014a6f5
        51dddaf7bd0fb3221cafeb50ef85eaba6fe91d796e9a625fb1d45300c36f1c54
        0334b690a63294b4fe17612b9eac9ef60e919a9a21b4d8b5b873e4311d330000
        0000000000000000
    """.trimIndent()
    final override val expectedUpdateSmallHash: String = """
        f4161585d94be8b4ba5f9e6d4814361c42fdcfbb605477f547f73a8fa6f83da8
        c5c0dc522aec47196ed32ae2509edd597c712f3eef0b3f1a07efc2c21e010132
        2924b3b4535f4295c4df55cc98c1243153431af592978c5b27df0058009256d2
        d80e3429a2d95c44b96cea60b243e583d8e95014a6f551dddaf7bd0fb3221caf
        eb50ef85eaba6fe91d796e9a625fb1d45300c36f1c540334b690a63294b4fe17
        612b9eac9ef60e919a9a21b4d8b5b873e4311d33c039fc86af842ee974e4f0d3
        74b46b70f7a158885eb1cf0c7898ee455912114e909af777c2c197d5003c12b6
        c43faf6afef92771f04814249858cadf24e8cf7d88bc9216a8d0feed7475368d
        d3c9fcf7142803548646e920651c929c01652dd5fc45f3025f8b2fd8fe43173d
        fbc5d3d0edcfa2ecc5aad55da0cd05bb458620aa113c34638e3a517dc02434ac
        6cbc15c897950f23b957b1ed0cab9075dd29815b6ccf383d686a9b21c1a5fbd0
        20b67dfd56b0b0ea3fabe8de11a9e8bbf77bc369f781f2d8f74f6c3d3df311fa
        d3ab7ed58112ad0355ed92b207f8726808dc24ae6cc8bff643bcbf098b6e8e21
        be1602196773e8b73605c7c20815a6e7718dd818b6e315d790074685a7084868
        31efb14648e930323e8d6a68068d0a6d630b1b2aa737fca4fd79db4f046c5949
        11f331658917e1c777feb4b01bd512c35510b955513865455f6a742a4a34c1eb
        1f61a0152582fe868c54a5d692d24890f561de531383747ed66bcf33f0ba6eb5
        1aae249f69a25c7d8e5716fc4cda377c464eac1b2dc59ea0d68482a6b50a4f09
        8c350d52
    """.trimIndent()
    final override val expectedUpdateMediumHash: String = """
        b8113406bf27e08c46d3f8588bdb2380be03ea6d63ff25821c8fcf41150abf1b
        d645e65a99d2db9b81b0dc30951b52c9706f40867bbad075b1ba9fe49b8f8505
        df746d81093a4b016d3c8da7b3a6b2cefba3a3be0a33b4a8859b9c24ad4ef112
        61e31bf954d18866c1ebfe24d56129e02584655bad301c89634eb1be46d25d58
        3574aca3a0d67c04063c446c69ec1683a3f5f9a7f67229bfda22823ef40d144e
        9fddeaadc9cbd398d40f73fe07f75fb3b45a78e4d7671a6c21eaa42ac8a65c39
        65b8c51db914a185c5da8df6e9628ed793c2b07b8a49ee50844939a169ea0a77
        3c50ab1648fda0ac268a23a6290c39acdae67e79113965e5eafe6f4890e258f0
        5813ee674861ce17138f49d704f6d72a5b5741217002442a229b6a5d91131d9d
        d60eb4a883991dbe720e432b7b6963aa06807f8b2fa873aeb8e33a74e5d61764
        91adcfce5ed0cb883a1774e3933702afb3e50b9db24bf6d4719397f46ba0f5a0
        20a8e48b5d5ef0a48ce94bab2040db2dec2ac0abd60698c07199a92c0a695fc5
        32d0fbb1acc8f8ca4962ce05b8bb51bcc904fa5c14a08187c81c0609cb56abb9
        2ef72448ac7b235aeeb104805b802db34e740e5b052f36d43bd9f0c1205ec1ff
        08f788ca181afd5385828544e6d2a2995181191de32559e0adcf89a2f80ded92
        e7cbc128524f1dedc30be836e42cbb55a843d49b406117b70192a658cea4deca
        e4b1e0f6e929c45f723b034c38f5ce2ad5b23ec5dd554c858444a8a773dffd54
        bbe279800b30bcc75fee0a7f83f488952b8fe4a06c6a7b50eb9baae2cc6fef2c
        284c9bde36348ad8225034ec754aced201a97b7383da6c93280e185aae25a1a6
        534b94d627bdba1339988f1b2f40952d06f7b895754f7d1bb8cf69d4c3293661
        1fd4310a3c7c0478937dc279efd3ffcf5abb3f31c13622ffcbc95b0a6fa40c88
        fe7e2829a8daeb2ebb216f437e6b99a03ca62e67ef908b19a5ad28c338da8ddf
        b90970f9e8b0356154943a05c7c2d5b040cc2fded44f104ca02c094e2053d0af
        1a0fcb0873924cf5a0d6bb68f9bfef2186b40549568b18030c76048349f5e063
        05ee7778c628930e51345b8bd9b107a1b7bf47dbb5fc027356c91ab72084885b
        27ad85377dd305f887080bbb928a71b3cf5b68d18424523c4b07f2a25ac9e4c6
        fabaccb0a9cc4f4706048672d642f5ec4bf26bce8f8da0b535929a16154b26f2
        5d2c3317fb2ffa28580c692224c7401d482a938f9d3b537746b69082c0cd7291
        471366c6a84e873e259b5f6795652d4e990a890c640f24ca
    """.trimIndent()

}
