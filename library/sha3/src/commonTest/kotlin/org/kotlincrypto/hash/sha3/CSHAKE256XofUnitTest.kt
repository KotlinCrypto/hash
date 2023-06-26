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

open class CSHAKE256XofUnitTest: XofUnitTest() {
    val N = "SOMETHING".encodeToByteArray()
    override val xof: Updatable = CSHAKE256.xOf(N, null)

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
        56259e24d1d2ca5400a86d12ad5eabe07b56775bca0e14c4df830246881a6261
        8cd0fbfeeedd9478a6a5a620b385df382e3075acb3617bed3b9b38aa8278607b
        fca90adb3d4aa1862bb09bf5b5855bdf9c1023f9b113f5b3790c77b2ac83dba8
        4b991fcaaa0be9077ce54c2e8916914ee2c03c9e5874a7a4f156ef3af9e3b7c0
        982597f2ee9998393709a5914cec7bdbe248f1a92b6feafadba6720ba9123891
        0b334020c1bd09f2baf6334d81000f060fbf6b4ffacf8d08dcdb849639808eba
        c2e571879cbc223c02293df3831bdcdd8486974ce48d2c206e97d04978495826
        652cb6190e6f7a62ff5a9669f4d47cf816b6d18f6c52153e2500b5fc3568a4c2
        edf87acb24957112218400de78c3e22a62e539ac10dc8098da4e7805a9552788
        4536d28005d69e60b25be7c853e94ee1571a1881c9b5ce14407b53e836f9236d
        aeeb8d19e3e1f4f2d8c306c2ec7b8c0f7bd059d8474195d9b8afe0ffaa9518e8
        b38c011aab8615725d68800c8e47a86a88090dee31527424aef78ec7764175dc
        a1f1c1867d1bcdc8d984db63e43470e989ca367ecf100942b91958933c3ca4f8
        2cbe2c71e988e149d56a275e6057f52573c3746e84cf0879ed08813645e634ad
        b0ea8634704a1087e9553e02671dd01926948b152e2ae82af29f62104f271c50
        a9499f08edbaff771b782965a52351c21628bb83e8d954a47ab475ec9c4a83eb
        30f841396a555583d2d6615bddad39cfd60925e139fa3f4c6eeae98e84e56c91
        07cbe9917ccb87c519090f457ca3c4154ef3c6eb5d40848d46e2bbcb2e40b2bd
        fb30afd9fd4cda488f2de3575d30e3d73277d86ed2936170c08a29ca7c2a4406
        e4b82bf74782fc8a2c5982ba188c9548b5056a740d283ec68d2b206554a6dcab
        4a63c4274b4182e9c483548dd527e9ea0c823246fcc550e1725b7a685ca9f3e0
        1a260d79772fcde028285e520b090d3cb3d814b7d30e70a8a0c4f47525803a67
        80e0b82597d2456dd0f068cac3f84ac4caf5b4e504bfb3b23ca723dfd5d199aa
        5b5f480cbfe07c2f55408077283f182d9cf859e005ba8ae379efda02c73447c2
        a9160cc915f2f572ab3e1b80f62db39f00d2965c60f88b887ef79a63c8134ed7
        3ced3c040aad5151105c82b99f41af028213d35250f221e97fc7e50e8875ae6c
        eeb94642dc38a43555f987b9ea1ec93bfea18095daf27a354c7378e7495b0e6b
        f73f38a5cc6237aeb7f6efc774c5528926be56ee5a1076e6ae83fdb5c7791f89
        63db0adb209697746b0f480249760e81076319dac548dca3de27666580f1bcd8
        ab4ec2f8dd0bad48da2e7bd09c4f8e9cae22952c27d845162609f90d25eddfd5
        b0125c0daf007caa61efb253e8f0eb2c5d83a2efef0f59e7b856bb606f9b2c34
        80df3e205d84298f690b9c739a49b35f04d30cd23cef4357b62f8a2552578655
        51b50189c0de2a46cb065ad65b2942fcbdbd83cb2c23926dc3a93bd2c9579929
        0db6fbcf0a3192daa890f47abaaa5a7e7fbe5b026acb6d61feb27ef71ac2e516
        723fb9dafe836682412aa7d4c5413ec3b6712c4b014a82b5daf13cbcaecdc566
        302413ac969d5ae450525cf40e159d2da9ecaa54f5e9708ce14ad4239ab0e17b
        b0a391fcd08022b6d21d5312d8b4939aa19c6369e75ae25895faeb87608a1967
        1a0774dc8cd25fc28363008e43e29ab72270d04aa2e825c0e3ad85a1f7724226
        0e2d0b3fd330f8587f
    """.trimIndent()
    final override val expectedPartialReadHash: String = """
        00000000000000000000af4c741ba13b8ed16ab66bca8ba3d27b8d59b8684287
        a3dff063684c513c8422b9a31b341ae5aefee2b57d68d509790004b2e519757d
        a5093f9ba5b6ea4ef3174096134cb958259e7aace099f1296952879e9f2c9d13
        9843ce7d4a2201fcfa36f01b59b7dd7845c20ea3ac0fe1802465208d73cf61ee
        2f2e21b67e36fa2daa308f728e7472ff605a50a3eac27ba0d54bf533ba7649a2
        a5b1750a646d64f399e0015f2fe681e6ee426a9cefb6e337b3ff21e653ba0000
        0000000000000000
    """.trimIndent()
    final override val expectedUpdateSmallHash: String = """
        af4c741ba13b8ed16ab66bca8ba3d27b8d59b8684287a3dff063684c513c8422
        b9a31b341ae5aefee2b57d68d509790004b2e519757da5093f9ba5b6ea4ef317
        4096134cb958259e7aace099f1296952879e9f2c9d139843ce7d4a2201fcfa36
        f01b59b7dd7845c20ea3ac0fe1802465208d73cf61ee2f2e21b67e36fa2daa30
        8f728e7472ff605a50a3eac27ba0d54bf533ba7649a2a5b1750a646d64f399e0
        015f2fe681e6ee426a9cefb6e337b3ff21e653ba892125d13801e3c584616064
        5b7aba255de50343d45fae588268a3efaa82c29eed7755c1197a8437009abc7a
        a9d0411a85ffae8a522e98dff3c36f610ef972672289ca87e438f6804ef1dab7
        74bf9b6b4a3027d7bf42828d7dc0604aafc8f90871f01ff02c0b406a4ac8ad2b
        bf9ce7df67693939adb38fc3c803d453ef62105217be1a8b8418a62369570c3b
        2e570a20bce7800a7981dbe79aee5406389ee087101654e786e646f2573906d8
        9834af1aade4893abac128d12f26832448817d5554e7f1e92519014b95666de2
        9592c03f161851f2a12d88a7f34b2552ea3f80908b9f5120fe39b870bbdc1db0
        99c0fa9a0a144c94d608617ea0d6afa1c58d2e6cb4ec20d1857f3181310773a5
        9c5d7ea7a871d7493a1e4cc9ad2847e67c8358ba1b102e31ff74020321776d7a
        80a9075cb6913268c4e9d85e5a798fc772074319236e6ad4572965a12c749cfa
        907ca77b4ee4ab2216047dfd5835f701d68f9f506de2d4fba9a4a8b725babf29
        90f7899fd0275ace56ef092e4c1de4c6a46a4cc2b3b814b46e71c7ab8516b1bf
        1541415c
    """.trimIndent()
    final override val expectedUpdateMediumHash: String = """
        7dd59bc3efe51455f6127396ebf44981860322d93118f40b65e92b078c9ecda9
        2b28e42ed354218d8b625f99cb14e11bc4c7d85440d5b0aa08ab18569e5fe224
        e902f845ced5054b2fadcca759475c9134a482d69fbc542c553957b50028ee3b
        b57755f71eb2bcbc9602da56a203e4bdba30151e5f25dd741786ab78f145c955
        a28d3e6880ef2b0e951f72a6f2e82f899c9126778e8893ab635b98ec4db653bb
        7f9b39fee7669befc3d6d3c3ec0c2d150e9532f79f3df3e296904b411521cc0b
        36c352219a8efb829a99260cbf0b9751def30269c954ebe99dc5e90d430b593b
        442b8b26838d858a2c9482b2f0204b6ba04a2af88e730933a81d45de9238c58e
        8e244b28d3569116b0c9b4a07bab5158ee3dede89e890bc01c3699f03318b44e
        77ac3cad25b0ad4be0cee7bebfb2e6dc8760e63e82bda3ff4c2e4117cdaf0a9c
        89dd3d54b6e5bdd1c30cb316d362a04a0a577653f9585f736f6bfd7ab5a3b2d9
        d350c3689a4999e2ef4f7134ce8a3573d383b48ca37f31e0bd89d66de09a6fcb
        d36bcaff765af423f3674090043d50f7dc08c0435898e814ff54c58477c4d70a
        ee7c3eda97158a4c756557c970fe495704c471a6446b6adea1ad69c3fd6b6a3c
        78c475b3a0816956a03f98bbd5a98c1495e70b44b486bfcdf71ba6af163093dc
        aea910dbe5a735fbf9b5d060b9b08c3bfb50fae6836aee6a6913ad8351e37471
        f0e41468188b2cca893954cc6b6dbaf138b933c7c8a78165849a018c69486995
        78b06b8f2e225a50f9c7b267d7aba7a3d87f0703dfbe73c4d492bf8e82ff23f2
        83f69135ad3e01302d41895d1bada5cfbc33693bfd11495492174ce44a0023c0
        cb31e3cb52ad91a32e911e74bcea7d58d4d26724a326e3691e119aa4791a465f
        7f5454b9796b6be5fcb033157692a955d4f8662028b35760c99ff68324102fda
        7e9949cef8ed0d635e2bba23cfff319f397612a6f9aa154aac7bd8bc2b7306e8
        fcc0a679b38523180223da5c3c28273245a070a9bfb22cb078a05d47f648ff49
        ffbf6048306ba8b72bfb4b415582a5619454caa687205439448220c11bfe0634
        8b9747568c6ec26e0aba113a38d7922963bad278fe2af934e0434818cf4c7c87
        2942898ac8f7e9f19ade517b310eef8172526704446306cb57e0fae2148eb6c6
        a015f761a437cfec29dc5a60527cbd3d15eaf7966c2fbdabfbf02930e1046caf
        e705c80ba40bce8e8922d535babae29bd16b0b125e0fcb04a96b6885bf490b24
        08da8a6a96791d93524a8fbaf26f228ba5f258dcf25798af
    """.trimIndent()

}
