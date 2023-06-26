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

open class TupleHash256XofUnitTest: XofUnitTest() {
    override val xof: Updatable = TupleHash256.xOf()

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
        deb3fda5e2d09c28fbd3508a7173dcc1850024a4adb918aaef220116f0c2b6a6
        efe6a8521a64fa3382ad465567fce2a799468ab6059633efc933d55b48184bb9
        a3fe9fc2a930adcde5d00b40cfbbd55934e49ea65a5eae8b37b9674117d9316b
        39983b186df4ad5c1fbaa94ecb3e98d6a82e27c4193341dfba6d7b57e3e6dc7b
        7df1363f45b4e718438662ef26351593ef1d4a038f74bf5219d5938ee3592311
        25b5a74e27be9e01e5d6b881fb4f8a36a318f3e7ed662961a021a0bfb0db41ed
        f94342b5b415c9ec9e19194b3cb1097cad8df758f2b6bcb3d6d25e49671e3486
        f28bb059829b46322fab43b15d993cb8512d4c8085c7214c360169916ac8722b
        0e22bc26e7ec2e5a9663c25e00dd64b172dab8864989eb7c4c864c24f1a00efe
        ae45563379d0b6edb6c2912079fe2128df3e953461b6b0a8612cb82a6a2386fc
        cca63d2d259724787935a030fb8e9d7edc8cccd0c9a3df1239613dd0f2403397
        e06ecbd00a963c431ef32e0db4142e360b7b50182468be5dcd23a8938b3bb68e
        2e5a737c5b051dddf952b64de7fdd32757f31d71926b086f43d5052bea2a4f19
        361f0fad5bc1df60a8c70f4f1d7f0a613136d52a9010429003e0c80586f1b847
        45b392a81e340ba3a0f74dcb7036b1f406ae1c52da80f4f5124e6c81787c2a6f
        e4f0c2e3f655430f9a5f83acb3413fc535ba3f34f3d71fd3f981ef9670781a1f
        63a80c06c97450e7e65a491fccf1bd36f515b681c917bbdc345488d661089cb9
        89a7e1d02457368c3636138cc6e0e3ef2f489e81e129201e3eaa6b254a49498a
        d5f45114863a20d846b58c9afb6e83064274bfb0a80c29224c22ea4088851456
        f94c1d3db1dd183cc46eb123832b63deb1dec464439d71a20f65a449395e3ea4
        b96674fa83824615f50d4d38a799065327ece732dd06041fc6308665b76e4aeb
        72b202e2160c1a5caedae1816b328979ba882c23892701d0105199a5a1993e8c
        804e02f7ebcdcf9410972d8b4b4473d38c2df5f1fff626b7af748784062540b6
        a723ce2aba42f56935da575b7e4fae174358db0ee0e461788b088c618ad04580
        b248dbf7623fdf26335a839008737c42352f22ac636d91d5eb57a51c0d7ad8d5
        30d6e015402d92eb62feedca34b9d8153893851839636996804f30914213900d
        18116a3e9ae1e8f59f4fd5dc043264a64186b73b90612f96fd4ae1f83d84b4d8
        8a1d725686909ac07f116054aff648b1ba27b0e6c770e724d8447e519f983374
        85f77baf0f45f49741c55962585b03009f6696270d5de0bf4fb7f0370685c287
        0acb0002474cc3e0b6c38cd5be0c04bc46106cb40ef71f59d4353ae8d65edf86
        b8df05f5fe557dacc89bb9e5c2ac0a057285bb42962f637e7f1b5613b08c13fc
        44a2125bd9fe94ecc7c0a6a4b0a135c9a4dae1f60f2ea6be8b85fb646aba8634
        51ec5d47d52f9a85eac4436d6c4993b22af19f1d517e84916c98e45a6244c16d
        dfe95f54a52aa8f42a4350d33bf79cb4af1d1ab1105a892dd38a9fbb8506fb0f
        f0ad63a10eed1149d19383d74ab1e13d65f7f0395ee826638e99a7b7c70ab07c
        da524c5288114dbcbec137c5a0c77bcb9487252a78108de84334537c4a286e1f
        df71f22ef36357ebaa93361cf68c6534d4732a63a43eb57e7cf208d53b620ea7
        9467f088fdf1a8a49488225a31e683a9a99068ae0eb7382faa17b59e043cf108
        d5fac9a88ac835dd2f
    """.trimIndent()
    final override val expectedPartialReadHash: String = """
        00000000000000000000688e00a39e4e9cd7545d53156d09c43718aca382d15e
        bb4530ec8f85e8f5ce1a67b36168e71a33411a4a01125119914b064af9f10228
        0e39c2f842dc65e7e6feb4ad3d182a1ae74ce2ef655122d3207781c4187919d8
        a51013504d4a10cd21e83284c7a699f075ef147988a7e3c165bd35b7e957ac2e
        dc801d1ce7704c0c62680b3b4ff13810ca7c68ce89dae2e0b1945b5ea0c5d76d
        a39e4d8b4d8bc9ad100ebd04d7b8c18d5519e99f070b061f0bbbfed175390000
        0000000000000000
    """.trimIndent()
    final override val expectedUpdateSmallHash: String = """
        688e00a39e4e9cd7545d53156d09c43718aca382d15ebb4530ec8f85e8f5ce1a
        67b36168e71a33411a4a01125119914b064af9f102280e39c2f842dc65e7e6fe
        b4ad3d182a1ae74ce2ef655122d3207781c4187919d8a51013504d4a10cd21e8
        3284c7a699f075ef147988a7e3c165bd35b7e957ac2edc801d1ce7704c0c6268
        0b3b4ff13810ca7c68ce89dae2e0b1945b5ea0c5d76da39e4d8b4d8bc9ad100e
        bd04d7b8c18d5519e99f070b061f0bbbfed175398b707efb65c0d4a38436bfff
        f1ea897560aaa4df8b025d0c008ffdeb75fe8cc297218ab0e64180af4def60a4
        6e65175518d885ee0ba117d7b5053c5bcf5cf85d0ceb8491ee4a57c7a3bc29da
        f2601311d4905e09b509b04fc92a5e656f7ae8607edb7357019f12664d455d47
        294a51d0a5b88c66432270884cc8768d54bcd3e92d850d3e0c749de0842c3bb3
        def19ae97957ec1d3bedb641b5d321f482bff70bdbfd3fc97b08dd470ecf9edd
        6eb8466a30d293ad9fc914dd4e2408c4896dac0bdf863353e85a90b8e243a693
        5cbb81eb054b0ec99ed487aa613e430966b796b7646ac66717845a10957d0ead
        d6f685313c729bc0f018b6644e47c6aadbaeced012164bca3434000db15f6bd7
        64f75cae6300468f8514d6fd4428536de071e801f1950de9f3fdbd7824604c34
        3d26ce23687484931a4f8793252ef57e69dd6be28f7ccb2c966ac364354403a4
        bd08b2665fc3adb5f543f98e54b5d4faedbf8ed32a9acc0ee94630084e3a4a2f
        387f1a254377ec174fe7ebcd0586c64d73c8a0a771be90e047c9657b988e0211
        8de9004d
    """.trimIndent()
    final override val expectedUpdateMediumHash: String = """
        4d30c31c8f640367b2c07ce385a2519b04a2afc18f996ad312ae2a7578b1eaf6
        8319b42f1c0f8c55bb685572ab8ed61b96936c467ee48389cf6d9e7429c00807
        13ae64d9465681a04d4c94325ca01ed214d6b2b58bed044be1df260704722ed0
        9f7076d671d57575f63f21880059538cd6e6f48a12db9c58d0ea29d171db8d48
        f47b90b119b087e62178ef62f6e88c4924c2787472590b6069176164e0439674
        1e1d0b517ef15ded63d550bf3bc888147380b574b3b5cf7b7ab558338515b751
        8853311f5eebf81d7c2855fc9171430be495edf90f9a84df081c6632c660c5c2
        5bc2f91c73b35bb550cb15c09fdc7862d2761ab2b71553e7df1538c45d97f409
        da02bbbbfa67d1e2e2bd514f4932d15d998053afd6ef2d77e3f9b4ead5279b79
        e4992de56ecd08fcfb3145c851a17b17f394df9c9a17746f4ccd57b8b298b6b9
        1d6781f41d32e1882e67918472fe1f0d8e22ff9c0c33963dea91d6f6083524f3
        b7263977635360f5ad948c427bad13fbb33ecd4c3375350c3c68dbf33d6374f1
        cfb11b9889c459566e9700757b467eefb3d0dcbb769a661cc76c596b2ac83ced
        6a7fad23570f714bf0f7f8719022d5f2dbb74eca294506b6b379a0a4d68608fd
        619dcf7573f1d31c82d62dbf04edca258674a4b5c18ba4828eca0ffa6fbf74ae
        85d2bd780f2eec32caee2833b515b7af2baacbfaea0303d3e01b0f3735e1b851
        dd32000443d2f1ce5c03a6a9c7a64270a2e99d7ad0571281ac996144d3c446e5
        1e9889c1c4bdc1fc5908840346935a659ba8af9ce11973162acaffd65509dafa
        fa010b62199a8f9c78b72574f39f83404c549459bc5c5281d0c63c0f244f6396
        83bdc9e35ac8e2cf5716a1515cf86c892ab5e643e58b52c2127297935b29c5b5
        c5b6d0b7c552b1dc553c07eedac4df251763a5dbabe1c13761b4cccbc1e8e108
        b734ac9218aae7a84999f9d75ff8bb34d30a215c4da402dd16c3f659bf6be724
        0c2e0051b89565cc7f0e41d5397e02876644c73f995f6137f543968329711b48
        ed15f5d81187dc33941eda0f7b9a85412a94beef182adaaad051e444cc813118
        d894a44adc230749e22f8a2d6b65260faf1baf57fd59ddbd0b81673ff4fcdc61
        96034490860b4e763f81945f68cc12ead669f5478be55bd4d72d68d28e497302
        39e8d0f9b976dd5d2f0a59e7f5a972b10aef16c0e0f78d2808b5bb5bef2c2dda
        097ba0f6bc58aadcc9e3ccf317220d635f031b5f5717abdfbd10fbb5ddcec464
        4419be6d9e1cb4ae536a7de95feb1c75b9505e040868546f
    """.trimIndent()

}
