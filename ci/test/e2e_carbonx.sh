#!/bin/bash
#
# Copyright (c) Microsoft Corporation. All rights reserved.
# Licensed under the MIT license.
#

SCRIPT_DIR="$(dirname "${BASH_SOURCE[0]}")"
SCRIPT_NAME="$(basename "${BASH_SOURCE[0]}")"

. "$SCRIPT_DIR/../functions.sh"
. "$SCRIPT_DIR/../test-harness.sh"

USAGE="
Usage: $SCRIPT_NAME binary_dir action(all|speech|intent) keySkyman regionSkyman keyLuis regionLuis

Expected environment variables:
  - \$TEST_AUDIO_FILE - audio input file
  - \$TEST_MODEL_ID - CRIS Model ID
  - \$TEST_SPEECH_ENDPOINT - speech endpoint
  - \$TEST_CRIS_ENDPOINT - CRIS endpoint
  - \$TEST_INTENT_HOMEAUTOMATION_APPID - intent home automation appId
  - \$TEST_INTENT_HOMEAUTOMATION_TURNON_INTENT - name of the turn-on intent
  - \$TEST_INTENT_HOMEAUTOMATION_TURNON_AUDIO - audio input for turning on something
"

[[ $# -eq 6 ]] || die "Error: wrong number of arguments.\n$USAGE"

for envVariable in TEST_{AUDIO_FILE,MODEL_ID,{SPEECH,CRIS}_ENDPOINT,INTENT_HOMEAUTOMATION_{APPID,TURNON_{INTENT,AUDIO}}}
do
  [[ -n ${!envVariable} ]] || die "Error: \$$envVariable not set.\n$USAGE"
done

BINARY_DIR=$1
Action=$2
KeySkyman=$3
RegionSkyman=$4
KeyLuis=$5
RegionLuis=$6

CARBONX=$BINARY_DIR/carbonx

# Expand actions if all is specified
if [[ $Action == all ]]; then
  Actions="speech intent"
else
  Actions="$Action"
fi

# Validate actions
for action in $Actions; do
  case $action in
    speech|intent)
      ;;
    *)
      echo Unknown action: $action 1>&2
      exit 1
  esac
done

# Using array of pairs for deterministic test order

modes=(
  "default"        ""
  "single"         "--single"
  "continuous:10"  "--continuous:10"
)

targets=(
  "baseModel"       ""
  "crisModel"       "--customSpeechModelId:$TEST_MODEL_ID"
  "speechEndpoint"  "--endpoint:$TEST_SPEECH_ENDPOINT"
  "crisEndpoint"    "--endpoint:$TEST_CRIS_ENDPOINT"
)

PLATFORMS_TO_RUN="$(joinArgs , {OSX-x64,Linux-x64,Windows-{x86,x64}}-{Debug,Release})"

PLATFORM=$SPEECHSDK_TARGET_PLATFORM-$SPEECHSDK_BUILD_CONFIGURATION

startTests TESTRUNNER test-carbonx "$PLATFORM" "$KeySkyman $KeyLuis"

startSuite TESTRUNNER "$(basename "$CARBONX" .exe)"

# The continuous tests are sampling for 10 seconds. Waiting for 30 seconds should be enough.
TIMEOUT_SECONDS=30

for action in $Actions; do
  for ((modeIndex = 0; modeIndex < ${#modes[@]}; modeIndex += 2)); do
    mode="${modes[$modeIndex]}"
    modeArg="${modes[$modeIndex + 1]}"
    for ((targetIndex = 0; targetIndex < ${#targets[@]}; targetIndex += 2)); do
      target="${targets[$targetIndex]}"
      targetArg="${targets[$targetIndex + 1]}"
      TEST_NAME="$action $mode $target"

      if [[ $action == intent ]]; then

        # Only do intent for base model
        if [[ $target != baseModel ]]; then
          continue
        fi

        EXTRA_ARGS="--subscription:$KeyLuis --region $RegionLuis --input $TEST_INTENT_HOMEAUTOMATION_TURNON_AUDIO --intentNames $TEST_INTENT_HOMEAUTOMATION_TURNON_INTENT --intentAppId $TEST_INTENT_HOMEAUTOMATION_APPID"
      else
        EXTRA_ARGS="--subscription:$KeySkyman --region $RegionSkyman --input $TEST_AUDIO_FILE"
      fi

      runTest TESTRUNNER "$TEST_NAME" "$PLATFORMS_TO_RUN" $TIMEOUT_SECONDS \
        $CARBONX $EXTRA_ARGS --$action $modeArg $targetArg
    done
  done
done

endSuite TESTRUNNER
endTests TESTRUNNER
