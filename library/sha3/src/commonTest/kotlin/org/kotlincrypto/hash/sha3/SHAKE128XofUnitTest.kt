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

open class SHAKE128XofUnitTest: XofUnitTest() {
    override val xof: Updatable = SHAKE128.xOf()

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
        7f9c2ba4e88f827d616045507605853ed73b8093f6efbc88eb1a6eacfa66ef26
        3cb1eea988004b93103cfb0aeefd2a686e01fa4a58e8a3639ca8a1e3f9ae57e2
        35b8cc873c23dc62b8d260169afa2f75ab916a58d974918835d25e6a435085b2
        badfd6dfaac359a5efbb7bcc4b59d538df9a04302e10c8bc1cbf1a0b3a5120ea
        17cda7cfad765f5623474d368ccca8af0007cd9f5e4c849f167a580b14aabdef
        aee7eef47cb0fca9767be1fda69419dfb927e9df07348b196691abaeb580b32d
        ef58538b8d23f87732ea63b02b4fa0f4873360e2841928cd60dd4cee8cc0d4c9
        22a96188d032675c8ac850933c7aff1533b94c834adbb69c6115bad4692d8619
        f90b0cdf8a7b9c264029ac185b70b83f2801f2f4b3f70c593ea3aeeb613a7f1b
        1de33fd75081f592305f2e4526edc09631b10958f464d889f31ba010250fda7f
        1368ec2967fc84ef2ae9aff268e0b1700affc6820b523a3d917135f2dff2ee06
        bfe72b3124721d4a26c04e53a75e30e73a7a9c4a95d91c55d495e9f51dd0b5e9
        d83c6d5e8ce803aa62b8d654db53d09b8dcff273cdfeb573fad8bcd45578bec2
        e770d01efde86e721a3f7c6cce275dabe6e2143f1af18da7efddc4c7b70b5e34
        5db93cc936bea323491ccb38a388f546a9ff00dd4e1300b9b2153d2041d205b4
        43e41b45a653f2a5c4492c1add544512dda2529833462b71a41a45be97290b6f
        4cffda2cf990051634a4b1edf6114fb49083c1fa3b302ee097f051266be69dc7
        16fdeef91b0d4ab2de525550bf80dc8a684bc3b5a4d46b7efae7afdc6292988d
        c9acae03f8634486c1abe2781aae4c02f3460d2cd4e6a463a2ba9562ee623cf0
        e9f82ab4d0b5c9d040a269366479dff0038abfaf2e0ff21f36968972e3f104dd
        cbe1eb831a87c213162e29b34adfa564d121e9f6e7729f4203fc5c6c22fa7a73
        50afddb620923a4a129b8acb19ea10f818c30e3b5b1c571fa79e57ee30438831
        6a02fcd93a0d8ee02bb85701ee4ff097534b502c1b12fbb95c8ccb2f548921d9
        9cc7c9fe17ac991b675e631144423eef7a5869168da63d1f4c21f650c02923bf
        d396ca6a5db541068624cbc5ffe208c0d1a74e1a29618d0bb60036f5249abfa8
        8898e393718d6efab05bb41279efcd4c5a0cc837ccfc22be4f725c081f6aa090
        749dba7077bae8d41af3fec5a6ee1b8adcd25e72de36434584ef567c643d3442
        94e8b2086b87f69c3bdc0d5969857082987ca1c63b7182e86898fb9b8039e75e
        da219e289331610369271867b145b2908293963cd677c9a1ae6ceb28289b254c
        deb76b12f33ce5cf3743131bfb550f0197bfe16aff92367227adc5074fe3dc0d
        8d116253980a38636bc9d29f799bbb2d76a0a5f138b8c73ba484d6588764e331
        d70c378c0641f2d9b6fd7c090df5a74604a1324ba0cc5c447b2dca644a50f1ad
        0477a701b9052ee9bef28833476343c82af29ff3a9b1c4cf12de559cb9d9411f
        62bec838121fd74bc1fa712d8add51505c55e89a35deaf7a69dc0a18ad273960
        29cbf89f513e1b8f48bc01783d6849fb32f211a4c87e16bcce0c41240a223ba6
        d69e0c51569f73cb107ead84d14dee92702e3a95eb844c716aec9829d06591eb
        d2501a3283cc0ffc0fdcc031fe8d865e77fae5d6bb73815d9ae376006d0ae320
        0c24d84c6b14a8471845a7522e7c3d0db9aea0cfce467be633d4d46a5086814d
        a467dc73403c987d8f
    """.trimIndent()
    final override val expectedPartialReadHash: String = """
        000000000000000000000b33a664b281d9a38638832fd314444c2fa5865072e6
        1505d0776c5cdd322ca3348d78e8721923f52450326a89b88c532ceddaf1b0e3
        2c47fee10aedfb35beec7cb3fb91b2497e4c228b286341e5a1b1348c2d1c8155
        8f0c6cf69ad2fac04d451407194f404e92922439824c1a32e77d7372316d2916
        074f2ed536883ed3b648c4b65cd8d6438419fe12230a50dac11952632676165e
        8d69f0d3fa0bf1c2cb6960be8d849a7e88da942c142ccb14db5e484808860000
        0000000000000000
    """.trimIndent()
    final override val expectedUpdateSmallHash: String = """
        0b33a664b281d9a38638832fd314444c2fa5865072e61505d0776c5cdd322ca3
        348d78e8721923f52450326a89b88c532ceddaf1b0e32c47fee10aedfb35beec
        7cb3fb91b2497e4c228b286341e5a1b1348c2d1c81558f0c6cf69ad2fac04d45
        1407194f404e92922439824c1a32e77d7372316d2916074f2ed536883ed3b648
        c4b65cd8d6438419fe12230a50dac11952632676165e8d69f0d3fa0bf1c2cb69
        60be8d849a7e88da942c142ccb14db5e484808869b2ab9f3e1329be4d5f14286
        82a5f245b7d63ceda34d2a04869c98906b84fae723084b296510c8e0d8b73c4c
        d95e6800a7c5cb4365147925b26cc56f75d19246c6bff0dad34576fc0a183f97
        06a2dd56ed1e5d4595c8d75885b92f6afb13ea58f4b1a42c62f5d7142c09068d
        3969b1c4d6ac5b7e3b007f0dcc98237362788ab429956afb2b1d6270e481f1a5
        330b89f7bae60162c15eb539b09f3c2c5517262a0f26ef829ae012a787f29731
        510db03b37ddc5d6df876b8f4eda9817ca68bd5532da273aa80be1dea491cb20
        b855247191326c5eb6b873757d65f2446de1aaa4a50665dd64187d83d77e6c74
        baf003a8b8be622f7227a8e5cedc18bb295b6ed59c2f7c2b715a0fe89e74f84e
        e29f0e1fb179d3bffe54bda114377f5a884c81d2562ce6524772cb741347ace0
        44140b423b40ee91d2231818de302dbb8c98363f8bf5ccf06fd9056664bc5563
        8dbca2e709851b49d919872504a0355077b1b189dc953796ce53ee47dacb58ea
        76458adad4478f0cb61f2c0e9ed2532e19ebc6c9e40703dafbbb88ca9e85adab
        36b5598d
    """.trimIndent()
    final override val expectedUpdateMediumHash: String = """
        9bf64f28a64f294769abd18b8903820453c9f31fe1bf620f95dede8f0b5d9f56
        64f73e035f05639eb29b72ee316d4f7fedbc906bad6aa0ce18541744843a3464
        f940995200b47219ce2f5c3a8145bbc42c4a76fb61b941aa534f5151a053afc5
        3f9cf696d399b6d8af2f5a6ea414c799b7f0079fe784d60dbc28971143367d3e
        e74973258103d72e31a52bf9d3de5de52c3e46368994a4b5a5e74342e134118e
        db6ae29e702860470e1f9a399e050fe918a7b965f9a89cf450170e99b4d06922
        65ef0191749570606afe2b41ab8c6ca2436d1a6a56f68d5387f320baae59d81f
        54c8832483ec085b61fe6e66d1996f5182e0b1b451c05973d2243811b17b3aa2
        f688819782bf4ab2c72e7ccb346173ae7ac63ebdf55a07ee5b193af64a222572
        fbd8df42789c42bce845f651521058eea3c3eba99e18d1b1161f2cb705c33b79
        a6ca6722aca7b3c0c5710a45cbd8a04b018a3f53ce79fe184d91e7eb5306a7c1
        6d0d5212adfb025b6f692e64ffca5b2354640f153c00edbeae042a33870e5c32
        724f3deb7e04c8e91490bf192ddbde391803eb33cdb6465e8fd1502d533e0dd3
        c3d6fba777b882953853aa689a7debd1ce00c9ba22e64e0504f340a4fa50b71b
        fcc22983ad01d133b24953eda78130ad9f09b81644fdd70794ca1b38d856e617
        96bc4e8ae3975ee2dd8385ee7ef0a257f14b79a7edfe67bd049a6d779e27331f
        c48cf47834c7bf8a3a868ddd1b92fc22b45d564edf35669672fe62ad1c73cdd7
        e807a7f21a5c9d605f88df83310b5cbf7749d5b6a9a813f0372ea1c9aaf29e54
        eeb98d40d580c9c6abe288d870441eec571c0fc9e5743efc82bfa6ee12e1283c
        0f0fe2faab7388a674d4ec298c4fa835e5babb92cb02b8f79b70d3ac7004609f
        93e2956a12026b5e6f16400c63e911a5ac8c6fff89370134c1284ca5cff45639
        82ca6a1200816705df58ec1afa711921c49bc51269045bd63d7563df8cbd5635
        620af9651b45ae7890c938b6f270f2e4f4a64ef7f7ed7cffa9a7b22c83751a1e
        6551b40e3696cd13bab8d8c6301683130b6bc6259f0c782ed496cb2557f7df7f
        70da3bd23c07a86eb4683e686134e67c24f071f33c94764decdfe3291a327731
        b66412e63439ae2cd6a462c308923abf7fe8923dbf40b713feafb93482ca25fc
        cb865d3972a7fefd30191eb41669d317486dbb64c691e6d2ece5f67a36310df1
        84801eabd38a327e60852b87067a4b948a7e40a3c88dc43b1ef3b35573c4c2b9
        9e115d5ccf8ccfa2096e771f0d09565ccb366c3b66fbe079
    """.trimIndent()

}
