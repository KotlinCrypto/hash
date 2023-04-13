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

open class TupleHash128XofUnitTest: XofUnitTest() {
    override val xof: Updatable = TupleHash128.xOf()

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
        d15d5f9a99da1149260426b0d43571d6244d19dc1296567ed34c90aca32a57ef
        36bcd780b974db5783a53d9ee5dc67f29bc5f5c59b6e0502a7a5d1079cb2aa8c
        d82d24521eb71d2d914effbbc5d38cff4c72e090f6fd09727016d8b3ff3a9b7e
        2cf9ad38cef2bf41f0f0f79834fb1739b6563bf93aa58691b1ff946d15e58790
        9ff8f5fcb6a60e9f9d204c0f3c37b4e85b6fdbf0da66e763caedfaffe62fe2cd
        591a7361c364d0b344858f3e9cbfc1f4ba3a503d4603933832582cdf6734589b
        68633dfdf85e787e5aff9ab2541c83b88a6f66cf07ed8ef5cad7158fe12722c8
        4f15c5242350f79cd4ab247a82efa4e0de128b14bea7d690ad84142cd720c27a
        3b00cb9ef22f8bbc54e67c4c18fd0b28d84cfb070e073ae2f2336ddb5dbb6aa3
        3964fc5552d3bce354d502e5c887adeff7397ca4d48b1d8a923beccc10ea087b
        a64818975279f92f61aa5c45e339d74dcb4e41d2c2bfc72520709a59f7fc5d18
        3b3e96c7852d2b6a635a611f8d0fec9741706a3743a609324342c8ed5c9aff42
        467d9107df5de94e333b0f2d1adc91d2caa2488cf9599df17ad898cc3d0cb24b
        6522800642cb400c478487a5e4c7e4f015d13f973fc662aafcd10f45c53165a7
        81fbe23f9e0760a6631d8c75d6d6a60d83d7372914a369caf9a70d9619c504b4
        cd38d3b7fd3bbc3ecce69e655516da3cf2b4172078428f0835d041de9afcf559
        9a81b44167473837ce9aecbbf10ae08959d976abad1bdb5ae8188276b99c739e
        15f467a324aa46211890f8305392eb176e5f6f86642d6445563494664f5b0522
        29efa26f5d1ae4d0ecd79de42fa7b11ad6c81197370831c5175aa0cad2d73342
        16a62cc5da1ac7115efd2a55a0e7f2ed4ed70298ea77a9a923bb446cdcd17bfa
        859bc8e79d05e04a77e0a68e0510597be69ff31419f14388b0b2819b0e1d5f1d
        cf91f20dc6bda3e1993dd6e08957afb2d8c256db8f61a420d837b9cab4c43f72
        ae5a9c3da3d32e63138d0fb637f6db97ee457ca517ff7ab4212edf3fe8b80eeb
        3e3f4ae86f1b5f4957e87101e35781dab82e8a2c4390551e90bd22fa1e8e7bbf
        77d514d37947e2e6dbd0e1d0468c0bf71ae631823b3466634b0d4897218def3e
        dc93f54e2e1b1cb5db37cb6cbb0425b7dd21b74d6ef2007fd5a333eb3bec1576
        dd9f5a23dcaf6e2339d93751a7a9c2039b4766acd2c96d9239925f8ec9cd95de
        2e826ecfda2e049128c2aba21b94d10b562502430656d0a486de81065a17656c
        1c9e188317d9b545a29c4fda1d0ef99295e577e4bf76b1335381d1032877ec8d
        f0e5f4b6ea97393ff773a824f7da508c509ae02ac458d56b6a173a588ec70e31
        a78fda87fbf3af756d36dded740313d599b6db84a4316215e8aadd515d5f84b2
        0e22c1a8f6ed7e1a5fdac403063f0f66f150fbf47b94687279d1b6af4c5bc519
        342aa34249911a10c4207e8fde98fcdcfb04557379e8789a250b8f15d7a3df92
        7931ec0500d24f8a9b7847dab0e8fa7ef6ceb015d8247d4e66eb8a52c143e5aa
        5fb8443da10ba7b30b75ff7821e4d816a2d68b909b805669f8ac0af1ee40a66a
        c2bb503135dc093fdb78f656218d37f5756633f0f6de6ec863f5473905ef153d
        4fb3bcbb1773a8004eb35586d065b6f45f5100292e0727d62019d976167fa851
        a1ae53f6132a926612ddab3d5d8ee6d673567f1adbd24bd3dc3aba980c599eee
        a38a3cd62e3d2e8999
    """.trimIndent()
    final override val expectedPartialReadHash: String = """
        0000000000000000000008f51e1d19bfa62e3885985f7568a5ea6381ff8a5747
        85e3d231508a61918e19ad123bba13737db085f746320d1a6f8fdf175a374b86
        e7b6575b1ce808d4e243e3c7acfd1b1a1f8634a68addc6003bf07b6e8b89f685
        2c4c07214473b8d362c1fe94be9dce7a16fff8dfc60b81aee54bd17f106e6243
        87898ede25492249e0ac6f98f643208066198a95e666d38342b5edb41d45e9a1
        0d0e20134cbaab5bb9a32521b60d54296eb632c65a9f4b23b13aa48119d80000
        0000000000000000
    """.trimIndent()
    final override val expectedUpdateSmallHash: String = """
        08f51e1d19bfa62e3885985f7568a5ea6381ff8a574785e3d231508a61918e19
        ad123bba13737db085f746320d1a6f8fdf175a374b86e7b6575b1ce808d4e243
        e3c7acfd1b1a1f8634a68addc6003bf07b6e8b89f6852c4c07214473b8d362c1
        fe94be9dce7a16fff8dfc60b81aee54bd17f106e624387898ede25492249e0ac
        6f98f643208066198a95e666d38342b5edb41d45e9a10d0e20134cbaab5bb9a3
        2521b60d54296eb632c65a9f4b23b13aa48119d8fd26a7eda18569b427fa3012
        d53f1c3fac6d7a77819fe9e1d4def2c78ff780ebd6c1004f2b9210bc7ec2ded7
        f291e9f7f25bafab071feff982a0503b53a81e365bf9b68c50a701ecac98463a
        a0642a1e939008994c0465babe7916c4a391d3e146a84139397fd1176f045f58
        e852bcb6220dbaf65fd1f0650ae4669123c16db0f42ff88ff7f03d204bb5fe73
        6f8067147524cebc2ace3235081a96d58b5d4d8387ea1ddb0240bec8d009b5e2
        7506cf42d24138da0c4ee74004b8cb2ed814abacc4f1d02718e45606d9df95f2
        0b01c3c9d11b244ba677e6d018d9ba6e105c0986c6c4f3ae0c0e83d17758ae87
        9d8b21e0098cb085bb352180c8356332779d1bb802c8f88215de8656504f9a29
        6394a31fd56a814ac6b3db30937a5a1388de727058f92a47aab665b5c0228115
        d1ad45d6ff9631c1a3d5fd2db72327c0c460a8cf7aa27e33d7c587c6e826d41f
        923f70a97b63cca2e6f76694be23e334c19ec0a862f413c44097dff26ff757c0
        c03c9969ddc34f2602ed7ad83ac7b57531d2029bf5258a2f752497ae5dd68b94
        a5a9b2b2
    """.trimIndent()
    final override val expectedUpdateMediumHash: String = """
        3fc525f00002cdb9a24e531a30afec39fe18ff9f4d01d160225864894adcb8fb
        a0f645cb7a994c98caeab4fb5948c0b6c51c7fc0fcf2b212ad5228a03e67474b
        869f7cd57965bc778e93c7383a3355493c609527b78a131ad9fee72385e0e5ed
        1ec26cc442b20936020cc3ef322ec9d02a6e4c8516b084bb08c697634a9080e3
        47a87c0ca608c98786358482207d69fdbcf246bd291dab0c06dfd17c6044d2ee
        52111a561aad2c0dd2e805867e6b31c6fa3cececad587d103e8f1e0ea8965bae
        e031b850fc63ca99cb7051ebda9fc43ea9fc7c0c64e6588ccbe1d5a6f2647fb3
        c5db7a84bfc55efb54504f02ee765e546ffdcba165023b07e3364b52f27c0c64
        dbb08cc2ce39dc73ed175c7957fcabcfe6d7a0ce6dc6295556235153686c5d67
        e8b5e5bcc506f6855ce9238a59b45ef72420fb5ffa60f813556bafe2c14c2c18
        e9aa2677f1e39792c53709c7c0f5e54018c3f3bcfb0bd6a6dda8c47beda09e18
        db1f3339fa6efdb9efb70e066215b318a679534bab098dba472408fcef8356ca
        ad45cb8395738189c491911bf55e832e202ddb7d9f43c92949d17fdd9429d3d4
        4b8851f1ad8f8964fa7d38a524d7661b09356bf05f9b979f66bf813e6350bea3
        195f31297e9c321a6ea2c01c21c77d55713adb9658a388a3b19e8a8a367b15ee
        2542eeb510b9e4f885d33bd78370939990da96b819ab21dbcf9db315e74251dc
        dc3e68b2e0fdbeda9bae03ca9e95173957b490524e46d44ccd6da50ff8e87eec
        ade31acd2bad08eb82c67045c68bf829880e152d1007bd0fb83292c7db9be64e
        c318b92113c215521dcc6e42b9be5f466c604c44164054fc99cc69330eb19153
        0973f2d60022198370da16fe3709b0575bf94c4d3fbe11988b2c3e7bb0731247
        fb02617f5031693236c33601bfdc41fdf5171dc94b300736b80c171f0c0b3e89
        8266f6ac34cd4e907296801c6562359c95dfaa99fe16d4595cd6944c9b643d4f
        0510f9693d92c0b2d7d423594a3603ad1227b2fe9f9e2c6c48cc7156e6b5b0a3
        4dbf7936d5ddf52ce67219a922f99d0eb428887cc1e1aa60d18d8dcd069815ef
        6a2595e7f0b0c893518be5aca7d21ef0d9ed9db8af4447b503460d203eab9a7e
        c836cba07536398caf1139da3f4f66410245c96bac2caeb4020f3f419612f1da
        77695b2af13ae0ce1721cde0ef030853563eb1c196c464acc8eeda3bf866080f
        bbb477b13dba7c18ad8d7152ccdd1a4907f3f40fcac86c7b5b0839e4ac426a97
        82b9978355fbeaecda44ce9e7cb60a703f916dcc48aa734f
    """.trimIndent()

}
