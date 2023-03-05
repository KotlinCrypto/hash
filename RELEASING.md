# Releasing

### Linux

- Ensure java version is greater than or equal to 11
```bash
java --version
```

- Set version variable in terminal shell
```bash
VERSION_NAME="<version name>"
```

- Create a release branch
```bash
git checkout master
git pull
git checkout -b release_"$VERSION_NAME"
```

- Update `VERSION_NAME` (remove `-SNAPSHOT`) and `VERSION_CODE` in root project's `gradle.properties` file

- Update `version` in project's `README.md` documentation

- Update the Kotlin Version compatibility matrix in project's `README.md` documentation

- Update `CHANGELOG.md`

- Commit Changes
```bash
git add --all
git commit -S -m "Prepare $VERSION_NAME release"
git tag -s "$VERSION_NAME" -m "Release v$VERSION_NAME"
```

- Make sure you have valid credentials in `~/.gradle/gradle.properties`
```
mavenCentralUsername=MyUserName
mavenCentralPassword=MyPassword
```

- Make sure you have GPG gradle config setup in `~/.gradle/gradle.properties` for signing
```
signing.gnupg.executable=gpg
signing.gnupg.useLegacyGpg=true
signing.gnupg.homeDir=/path/to/.gnupg/
signing.gnupg.optionsFile=/path/to/.gnupg/gpg.conf
signing.gnupg.keyName=0x61471B8AB3890961
```

- Make sure GPG is picking up YubiKey to sign releases
```bash
gpg --card-status
```

- Disable YubiKey touch for signing
```bash
ykman openpgp keys set-touch sig off
```

- Perform a clean build
```bash
./gradlew clean -DKMP_TARGETS_ALL
./gradlew build -DKMP_TARGETS_ALL
```

- Publish
```bash
./gradlew publishAllPublicationsToMavenCentralRepository --no-daemon --no-parallel -DKMP_TARGETS_ALL
```

- Push release branch to repo (to publish from macOS)
```bash
git push -u origin release_"$VERSION_NAME"
```

### Macos

- Spin up VM of macOS and ensure USB pass through worked for the YubiKey
    - Should ask for PIN to log in

- Sign a random `.txt` file (gpg tty for YubiKey PIN + gradle build don't mix)
```shell
gpg --sign --armor --detach ~/Documents/hello.txt
```

- Ensure java version is greater than or equal to 11
```shell
java --version
```

- Ensure you are in a `bash` shell
```shell
bash
```

- Set version variable in terminal shell
```bash
VERSION_NAME="<version name>"
```

- Pull the latest code from release branch
```bash
git checkout master
git pull
git checkout release_"$VERSION_NAME"
```

- Make sure you have valid credentials in `~/.gradle/gradle.properties`
```
mavenCentralUsername=MyUserName
mavenCentralPassword=MyPassword
```

- Make sure you have GPG gradle config setup in `~/.gradle/gradle.properties` for signing
```
signing.gnupg.executable=gpg
signing.gnupg.useLegacyGpg=true
signing.gnupg.homeDir=/path/to/.gnupg/
signing.gnupg.optionsFile=/path/to/.gnupg/gpg.conf
signing.gnupg.keyName=0x61471B8AB3890961
```

- Perform a clean build
```bash
MACOS_TARGETS="JVM,JS,IOS_ARM32,IOS_ARM64,IOS_X64,IOS_SIMULATOR_ARM64,MACOS_ARM64,MACOS_X64,TVOS_ARM64,TVOS_X64,TVOS_SIMULATOR_ARM64,WATCHOS_ARM32,WATCHOS_ARM64,WATCHOS_DEVICE_ARM64,WATCHOS_X64,WATCHOS_X86,WATCHOS_SIMULATOR_ARM64,WASM,WASM_32"
./gradlew clean -PKMP_TARGETS="$MACOS_TARGETS"
./gradlew build -PKMP_TARGETS="$MACOS_TARGETS"
```

- Publish macOS build
```bash
PUBLISH_TASKS=$(./gradlew tasks -PKMP_TARGETS="$MACOS_TARGETS" |
  grep "ToMavenCentralRepository" |
  cut -d ' ' -f 1 |
  grep -e "publishIos" -e "publishMacos" -e "publishTvos" -e "publishWatchos"
)
./gradlew $PUBLISH_TASKS -PKMP_TARGETS="$MACOS_TARGETS"
```

### Linux

- Re-enable YubiKey touch for signing
```bash
ykman openpgp keys set-touch sig on
```

- Close publications (Don't release yet)
    - Login to Sonatype OSS Nexus: [oss.sonatype.org](https://s01.oss.sonatype.org/#stagingRepositories)
    - Click on **Staging Repositories**
    - Select all Publications
    - Click **Close** then **Confirm**
    - Wait a bit, hit **Refresh** until the *Status* changes to *Closed*

- Check Publication
```bash
./gradlew clean -DKMP_TARGETS_ALL
./gradlew :tools:check-publication:build --refresh-dependencies -PCHECK_PUBLICATION -DKMP_TARGETS_ALL
```

- **Release** publications from Sonatype OSS Nexus StagingRepositories manager

- Merge release branch to `master`
```bash
git checkout master
git pull
git merge --no-ff -S release_"$VERSION_NAME"
```

- Update `VERSION_NAME` (add `-SNAPSHOT`) and `VERSION_CODE` in root project's `gradle.properties` file

- Commit changes
```bash
git add --all
git commit -S -m "Prepare for next development iteration"
```

- Push Changes
```bash
git push
```

- Push Tag
```bash
git push origin "$VERSION_NAME"
```

- Delete release branch on GitHub

- Delete local release branch
```bash
git branch -D release_"$VERSION_NAME"
```

### Macos

- Checkout master
```bash
git checkout master
git pull
```

- Delete local release branch
```bash
git branch -D release_"$VERSION_NAME"
```

- Shutdown VMs (if not needed anymore)

### Linux

- Wait for releases to become available on [MavenCentral](https://repo1.maven.org/maven2/org/kotlincrypto/hash/)
- Draft new release on GitHub
    - Enter the release name <VersionName> as tag and title
    - Have the description point to the changelog
