# decodr

A RESTful service for encoding/decoding ASCII text using (and showing) RSA algorithm. After 20 years, this code will be awaken.

* Show RSA algorithms
* Show ECC algorithms
* Show Bitcoin algorithrms

# RSA Algorithm server

* TBD

# ECC Algorithm server

* TBD

# Bitcoin node server

* Running the node server.

Verify some of the references:

* https://andersbrownworth.com/blockchain/coinbase
* Bitcoin genesis block: https://blockstream.info/block/000000000019d6689c085ae165831e934ff763ae46a2a6c172b3f1b60a8ce26f
* Tutorials: https://developer.bitcoin.org/examples/intro.html

# Build

```console
docker-compose -f docker-compose-bitcoin-server.yaml build
```

# Setup Configs

* Generate a user with credentials

```console
$ curl -sSL https://raw.githubusercontent.com/bitcoin/bitcoin/master/share/rpcauth/rpcauth.py | python - supercash
String to be appended to bitcoin.conf:
rpcauth=supercash:088b4ae065ce2e7baaa1f05abd7b9d31$c516aaa048caed816c99b84e58256194456d5cb0c359aa12e9b90eb829c85afc
Your password:
e57e2RenYPb-ZZMdk-YLDdO-5CcuIPIIyk-TI99vvTc=
```

* Configure `bitcoin.conf`

```properties
$ cat bitcoin/conf/bitcoin.conf
```

# Start the server

```console
docker-compose -f docker-compose-bitcoin-server.yaml up -d
```

* Verify with the logs

```console
docker-compose -f docker-compose-bitcoin-node.yaml logs
$ docker-compose -f docker-compose-bitcoin-server.yaml logs
Attaching to bitcoin-node
bitcoin-node    | /entrypoint.sh: assuming arguments for bitcoind
bitcoin-node    | /entrypoint.sh: setting data directory to /var/lib/bitcoin-core
bitcoin-node    |
bitcoin-node    | Error: Config setting for -rpcbind only applied on signet network when in [signet] section.
bitcoin-node    |
bitcoin-node    | /entrypoint.sh: assuming arguments for bitcoind
bitcoin-node    | /entrypoint.sh: setting data directory to /var/lib/bitcoin-core
bitcoin-node    |
bitcoin-node    | Error: Config setting for -rpcbind only applied on signet network when in [signet] section.
bitcoin-node    |
bitcoin-node    | /entrypoint.sh: assuming arguments for bitcoind
bitcoin-node    | /entrypoint.sh: setting data directory to /var/lib/bitcoin-core
bitcoin-node    |
bitcoin-node    | Bitcoin Core starting
bitcoin-node    | /entrypoint.sh: assuming arguments for bitcoind
bitcoin-node    | /entrypoint.sh: setting data directory to /var/lib/bitcoin-core
bitcoin-node    |
bitcoin-node    | Bitcoin Core starting
bitcoin-node    | /entrypoint.sh: assuming arguments for bitcoind
bitcoin-node    | /entrypoint.sh: setting data directory to /var/lib/bitcoin-core
bitcoin-node    |
bitcoin-node    | 2021-10-10T07:35:56Z Bitcoin Core version v0.21.1.0-g194b9b8792d9b0798fdb570b79fa51f1d1f5ebaf (release build)
bitcoin-node    | 2021-10-10T07:35:56Z Signet derived magic (message start): 0a03cf40
bitcoin-node    | 2021-10-10T07:35:56Z Assuming ancestors of block 0000002a1de0f46379358c1fd09906f7ac59adf3712323ed90eb59e4c183c020 have valid signatures.
bitcoin-node    | 2021-10-10T07:35:56Z Setting nMinimumChainWork=00000000000000000000000000000000000000000000000000000019fd16269a
bitcoin-node    | 2021-10-10T07:35:56Z Using the 'sse4(1way),sse41(4way),avx2(8way)' SHA256 implementation
bitcoin-node    | 2021-10-10T07:35:56Z Using RdRand as an additional entropy source
bitcoin-node    | 2021-10-10T07:35:56Z Default data directory /home/bitcoin/.bitcoin
bitcoin-node    | 2021-10-10T07:35:56Z Using data directory /var/lib/bitcoin-core/signet
bitcoin-node    | 2021-10-10T07:35:56Z Config file: /var/lib/bitcoin-core/bitcoin.conf
bitcoin-node    | 2021-10-10T07:35:56Z Config file arg: [signet] rest="1"
bitcoin-node    | 2021-10-10T07:35:56Z Config file arg: [signet] rpcallowip="0.0.0.0/24"
bitcoin-node    | 2021-10-10T07:35:56Z Config file arg: [signet] rpcauth=****
bitcoin-node    | 2021-10-10T07:35:56Z Config file arg: [signet] rpcbind=****
bitcoin-node    | 2021-10-10T07:35:56Z Config file arg: [signet] server="1"
bitcoin-node    | 2021-10-10T07:35:56Z Config file arg: [signet] signet="1"
bitcoin-node    | 2021-10-10T07:35:56Z Command-line arg: datadir="/var/lib/bitcoin-core"
bitcoin-node    | 2021-10-10T07:35:56Z Command-line arg: printtoconsole=""
bitcoin-node    | 2021-10-10T07:35:56Z Command-line arg: signet="1"
bitcoin-node    | 2021-10-10T07:35:56Z Using at most 125 automatic connections (1048576 file descriptors available)
bitcoin-node    | 2021-10-10T07:35:56Z Using 16 MiB out of 32/2 requested for signature cache, able to store 524288 elements
bitcoin-node    | 2021-10-10T07:35:56Z Using 16 MiB out of 32/2 requested for script execution cache, able to store 524288 elements
bitcoin-node    | 2021-10-10T07:35:56Z Script verification uses 7 additional threads
bitcoin-node    | 2021-10-10T07:35:56Z scheduler thread start
bitcoin-node    | 2021-10-10T07:35:56Z WARNING: the RPC server is not safe to expose to untrusted networks such as the public internet
bitcoin-node    | 2021-10-10T07:35:56Z HTTP: creating work queue of depth 16
bitcoin-node    | 2021-10-10T07:35:56Z Using random cookie authentication.
bitcoin-node    | 2021-10-10T07:35:56Z Generated RPC authentication cookie /var/lib/bitcoin-core/signet/.cookie
bitcoin-node    | 2021-10-10T07:35:56Z Using rpcauth authentication.
bitcoin-node    | 2021-10-10T07:35:56Z HTTP: starting 4 worker threads
bitcoin-node    | 2021-10-10T07:35:56Z Using wallet directory /var/lib/bitcoin-core/signet/wallets
bitcoin-node    | 2021-10-10T07:35:56Z init message: Verifying wallet(s)...
bitcoin-node    | 2021-10-10T07:35:56Z init message: Loading banlist...
bitcoin-node    | 2021-10-10T07:35:56Z ERROR: DeserializeFileDB: Failed to open file /var/lib/bitcoin-core/signet/banlist.dat
bitcoin-node    | 2021-10-10T07:35:56Z Invalid or missing banlist.dat; recreating
bitcoin-node    | 2021-10-10T07:35:56Z SetNetworkActive: true
bitcoin-node    | 2021-10-10T07:35:56Z Using /16 prefix for IP bucketing
bitcoin-node    | 2021-10-10T07:35:56Z Cache configuration:
bitcoin-node    | 2021-10-10T07:35:56Z * Using 2.0 MiB for block index database
bitcoin-node    | 2021-10-10T07:35:56Z * Using 8.0 MiB for chain state database
bitcoin-node    | 2021-10-10T07:35:56Z * Using 440.0 MiB for in-memory UTXO set (plus up to 286.1 MiB of unused mempool space)
bitcoin-node    | 2021-10-10T07:35:56Z init message: Loading block index...
bitcoin-node    | 2021-10-10T07:35:56Z Switching active chainstate to Chainstate [ibd] @ height -1 (null)
bitcoin-node    | 2021-10-10T07:35:56Z Opening LevelDB in /var/lib/bitcoin-core/signet/blocks/index
bitcoin-node    | 2021-10-10T07:35:56Z Opened LevelDB successfully
bitcoin-node    | 2021-10-10T07:35:56Z Using obfuscation key for /var/lib/bitcoin-core/signet/blocks/index: 0000000000000000
bitcoin-node    | 2021-10-10T07:35:56Z LoadBlockIndexDB: last block file = 0
bitcoin-node    | 2021-10-10T07:35:56Z LoadBlockIndexDB: last block file info: CBlockFileInfo(blocks=0, size=0, heights=0...0, time=1970-01-01...1970-01-01)
bitcoin-node    | 2021-10-10T07:35:56Z Checking all blk files are present...
bitcoin-node    | 2021-10-10T07:35:56Z Initializing databases...
bitcoin-node    | 2021-10-10T07:35:56Z Pre-allocating up to position 0x1000000 in blk00000.dat
bitcoin-node    | 2021-10-10T07:35:56Z Opening LevelDB in /var/lib/bitcoin-core/signet/chainstate
bitcoin-node    | 2021-10-10T07:35:56Z Opened LevelDB successfully
bitcoin-node    | 2021-10-10T07:35:56Z Wrote new obfuscate key for /var/lib/bitcoin-core/signet/chainstate: 81c2b796dbcc3123
bitcoin-node    | 2021-10-10T07:35:56Z Using obfuscation key for /var/lib/bitcoin-core/signet/chainstate: 81c2b796dbcc3123
bitcoin-node    | 2021-10-10T07:35:56Z init message: Rewinding blocks...
bitcoin-node    | 2021-10-10T07:35:56Z  block index              68ms
bitcoin-node    | 2021-10-10T07:35:56Z loadblk thread start
bitcoin-node    | 2021-10-10T07:35:56Z UpdateTip: new best=00000008819873e925422c1ff0f99f7cc9bbb232af63a077a480a3633bee1ef6 height=0 version=0x00000001 log2_work=22.206105 tx=1 date='2020-09-01T00:00:00Z' progress=0.000017 cache=0.0MiB(0txo)
bitcoin-node    | 2021-10-10T07:35:56Z block tree size = 1
bitcoin-node    | 2021-10-10T07:35:56Z nBestHeight = 0
bitcoin-node    | 2021-10-10T07:35:56Z Failed to open mempool file from disk. Continuing anyway.
bitcoin-node    | 2021-10-10T07:35:56Z Bound to [::]:38333
bitcoin-node    | 2021-10-10T07:35:56Z torcontrol thread start
bitcoin-node    | 2021-10-10T07:35:56Z loadblk thread exit
bitcoin-node    | 2021-10-10T07:35:56Z Bound to 0.0.0.0:38333
bitcoin-node    | 2021-10-10T07:35:56Z Bound to 127.0.0.1:38334
bitcoin-node    | 2021-10-10T07:35:56Z init message: Loading P2P addresses...
bitcoin-node    | 2021-10-10T07:35:56Z ERROR: DeserializeFileDB: Failed to open file /var/lib/bitcoin-core/signet/peers.dat
bitcoin-node    | 2021-10-10T07:35:56Z Invalid or missing peers.dat; recreating
bitcoin-node    | 2021-10-10T07:35:56Z ERROR: DeserializeFileDB: Failed to open file /var/lib/bitcoin-core/signet/anchors.dat
bitcoin-node    | 2021-10-10T07:35:56Z 0 block-relay-only anchors will be tried for connections.
bitcoin-node    | 2021-10-10T07:35:56Z init message: Starting network threads...
bitcoin-node    | 2021-10-10T07:35:56Z net thread start
bitcoin-node    | 2021-10-10T07:35:56Z dnsseed thread start
bitcoin-node    | 2021-10-10T07:35:56Z Loading addresses from DNS seed 2a01:7c8:d005:390::5
bitcoin-node    | 2021-10-10T07:35:56Z addcon thread start
bitcoin-node    | 2021-10-10T07:35:56Z opencon thread start
bitcoin-node    | 2021-10-10T07:35:56Z msghand thread start
bitcoin-node    | 2021-10-10T07:35:56Z init message: Done loading
bitcoin-node    | 2021-10-10T07:35:57Z Loading addresses from DNS seed v7ajjeirttkbnt32wpy3c6w3emwnfr3fkla7hpxcfokr3ysd3kqtzmqd.onion:38333
bitcoin-node    | 2021-10-10T07:35:57Z Loading addresses from DNS seed 178.128.221.177
bitcoin-node    | 2021-10-10T07:35:57Z 0 addresses found from DNS seeds
bitcoin-node    | 2021-10-10T07:35:57Z dnsseed thread exit
bitcoin-node    | 2021-10-10T07:35:58Z Cannot create socket for v7ajjeirttkbnt32wpy3c6w3emwnfr3fkla7hpxcfokr3ysd3kqtzmqd.onion:38333: unsupported network
bitcoin-node    | 2021-10-10T07:35:59Z New outbound peer connected: version: 70016, blocks=59178, peer=0 (full-relay)
bitcoin-node    | 2021-10-10T07:36:00Z New outbound peer connected: version: 70016, blocks=59178, peer=1 (full-relay)
bitcoin-node    | 2021-10-10T07:36:01Z Synchronizing blockheaders, height: 2000 (~3.36%)
bitcoin-node    | 2021-10-10T07:36:01Z Synchronizing blockheaders, height: 4000 (~6.73%)
bitcoin-node    | 2021-10-10T07:36:01Z Synchronizing blockheaders, height: 6000 (~10.09%)
bitcoin-node    | 2021-10-10T07:36:01Z New outbound peer connected: version: 70016, blocks=59178, peer=2 (full-relay)
bitcoin-node    | 2021-10-10T07:36:02Z Synchronizing blockheaders, height: 8000 (~13.46%)
bitcoin-node    | 2021-10-10T07:36:02Z Synchronizing blockheaders, height: 10000 (~16.89%)
bitcoin-node    | 2021-10-10T07:36:02Z Synchronizing blockheaders, height: 12000 (~20.26%)
bitcoin-node    | 2021-10-10T07:36:02Z Pre-allocating up to position 0x100000 in rev00000.dat
bitcoin-node    | 2021-10-10T07:36:02Z UpdateTip: new best=00000086d6b2636cb2a392d45edc4ec544a10024d30141c9adf4bfd9de533b53 height=1 version=0x20000000 log2_work=23.206105 tx=2 date='2020-09-01T00:12:01Z' progress=0.000035 cache=0.0MiB(1txo)
bitcoin-node    | 2021-10-10T07:36:02Z UpdateTip: new best=00000032bb881de703dcc968e8258080c7ed4a2933e3a35888fa0b2f75f36029 height=2 version=0x20000000 log2_work=23.791067 tx=3 date='2020-09-01T00:12:23Z' progress=0.000052 cache=0.0MiB(2txo)
bitcoin-node    | 2021-10-10T07:36:02Z UpdateTip: new best=000000e8daac2a2e973ecaab46dc948181c638adecf5ae0fd5d3e13aa14364b0 height=3 version=0x20000000 log2_work=24.206105 tx=4 date='2020-09-01T00:12:55Z' progress=0.000070 cache=0.0MiB(3txo)
bitcoin-node    | 2021-10-10T07:36:02Z UpdateTip: new best=00000194763f1233e40afd5f6eb2e64abd2e5e158a4139198a224f8a7503d47e height=4 version=0x20000000 log2_work=24.528033 tx=5 date='2020-09-01T00:13:27Z' progress=0.000087 cache=0.0MiB(4txo)
bitcoin-node    | 2021-10-10T07:36:02Z UpdateTip: new best=000003776c984edd753b876f0e70d08450f92dd76a768df03d2c42d1880aea5b height=5 version=0x20000000 log2_work=24.791067 tx=6 date='2020-09-01T00:13:42Z' progress=0.000105 cache=0.0MiB(5txo)
```

# Run bitcoin commands

## getmininginfo

```console
$ docker-compose -f docker-compose-bitcoin-server.yaml exec bitcoin-node bitcoin-cli -rpcconnect=localhost -signet -rpcuser=supercash -rpcpassword=e57e2RenYPb-ZZMdk-YLDdO-5CcuIPIIyk-TI99vvTc= getmininginfo
{
  "blocks": 50782,
  "difficulty": 0.002744736605802633,
  "networkhashps": 19574.2131698745,
  "pooledtx": 0,
  "chain": "signet",
  "warnings": ""
}
```

## getblockcount

```console
$ docker-compose -f docker-compose-bitcoin-server.yaml exec bitcoin-node bitcoin-cli -rpcconnect=localhost -signet -rpcuser=supercash -rpcpassword=e57e2RenYPb-ZZMdk-YLDdO-5CcuIPIIyk-TI99vvTc= getblockcount
59178
```

## createwallet

* no wallet

```console
$ docker-compose -f docker-compose-bitcoin-server.yaml exec bitcoin-node bitcoin-cli -rpcconnect=localhost -regtest -rpcuser=supercash -rpcpassword=e57e2RenYPb-ZZMdk-YLDdO-5CcuIPIIyk-TI99vvTc= getbalance
error code: -18
error message:
No wallet is loaded. Load a wallet using loadwallet or create a new one with createwallet. (Note: A default wallet is no longer automatically created)
```

* create one

```console
$ docker-compose -f docker-compose-bitcoin-server.yaml exec bitcoin-node bitcoin-cli -rpcconnect=localhost -regtest -rpcuser=supercash -rpcpassword=e57e2RenYPb-ZZMdk-YLDdO-5CcuIPIIyk-TI99vvTc= createwallet marcello
{
  "name": "marcello",
  "warning": ""
}
```

* get balance

```console
$ docker-compose -f docker-compose-bitcoin-server.yaml exec bitcoin-node bitcoin-cli -rpcconnect=localhost -regtest -rpcuser=supercash -rpcpassword=e57e2RenYPb-ZZMdk-YLDdO-5CcuIPIIyk-TI99vvTc= getbalance
0.00000000
```

* Get the genesis block information

```console
☁️  aws-cli@2.2.32 🔖 aws-iam-authenticator@0.5.3
☸️  kubectl@1.21.3 📛 kustomize@v4.3.0 🎡 helm@3.6.3 👽 argocd@2.0.5 ✈️  glooctl@1.8.10  🐙 docker-compose@1.29.2
👤 supercash-marcello-root 🗂️   🌎 sa-east-1
🏗  1.21.3 🔐 docker-desktop 🍱 default
~/dev/github.com/marcellodesales/decodr-rsa on  master! 📅 10-10-2021 ⌚00:41:54
$ docker-compose -f docker-compose-bitcoin-server.yaml exec bitcoin-node bitcoin-cli -rpcconnect=localhost -signet -rpcuser=supercash -rpcpassword=e57e2RenYPb-ZZMdk-YLDdO-5CcuIPIIyk-TI99vvTc= getbalance
error code: -18
error message:
No wallet is loaded. Load a wallet using loadwallet or create a new one with createwallet. (Note: A default wallet is no longer automatically created)

$ docker-compose -f docker-compose-bitcoin-server.yaml exec bitcoin-node bitcoin-cli -rpcconnect=localhost -signet -rpcuser=supercash -rpcpassword=e57e2RenYPb-ZZMdk-YLDdO-5CcuIPIIyk-TI99vvTc= getblock 00000008819873e925422c1ff0f99f7cc9bbb232af63a077a480a3633bee1ef6
{
  "hash": "00000008819873e925422c1ff0f99f7cc9bbb232af63a077a480a3633bee1ef6",
  "confirmations": 59179,
  "strippedsize": 285,
  "size": 285,
  "weight": 1140,
  "height": 0,
  "version": 1,
  "versionHex": "00000001",
  "merkleroot": "4a5e1e4baab89f3a32518a88c31bc87f618f76673e2cc77ab2127b7afdeda33b",
  "tx": [
    "4a5e1e4baab89f3a32518a88c31bc87f618f76673e2cc77ab2127b7afdeda33b"
  ],
  "time": 1598918400,
  "mediantime": 1598918400,
  "nonce": 52613770,
  "bits": "1e0377ae",
  "difficulty": 0.001126515290698186,
  "chainwork": "000000000000000000000000000000000000000000000000000000000049d414",
  "nTx": 1,
  "nextblockhash": "00000086d6b2636cb2a392d45edc4ec544a10024d30141c9adf4bfd9de533b53"
}
```
