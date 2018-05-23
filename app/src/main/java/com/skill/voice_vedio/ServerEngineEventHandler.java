package com.skill.voice_vedio;

/**
 * Created by eviewlake on 2018/5/22.
 */

import android.content.Context;

import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class ServerEngineEventHandler {
    public ServerEngineEventHandler(Context ctx, LocalEngineConfig config) {
        this.mContext = ctx;
        this.mConfig = config;
    }

    private final LocalEngineConfig mConfig;

    private final Context mContext;

    private final ConcurrentHashMap<ServerEngineEventHandler_ActivityCallBackHander, Integer> mEventHandlerList = new ConcurrentHashMap<>();

    public void addEventHandler(ServerEngineEventHandler_ActivityCallBackHander handler) {
        this.mEventHandlerList.put(handler, 0);
    }

    public void removeEventHandler(ServerEngineEventHandler_ActivityCallBackHander handler) {
        this.mEventHandlerList.remove(handler);
    }

    final IRtcEngineEventHandler mRtcEventHandler = new IRtcEngineEventHandler() {
        private final Logger log = LoggerFactory.getLogger(this.getClass());

        @Override
        public void onFirstRemoteVideoDecoded(int uid, int width, int height, int elapsed) {
            log.debug("onFirstRemoteVideoDecoded " + (uid & 0xFFFFFFFFL) + " " + width + " " + height + " " + elapsed);

        }

        @Override
        public void onFirstLocalVideoFrame(int width, int height, int elapsed) {
            log.debug("onFirstLocalVideoFrame " + width + " " + height + " " + elapsed);
        }

        @Override
        public void onUserJoined(int uid, int elapsed) {
            log.debug("onUserJoined " + uid );
            Iterator<ServerEngineEventHandler_ActivityCallBackHander> it = mEventHandlerList.keySet().iterator();
            while (it.hasNext()) {
                ServerEngineEventHandler_ActivityCallBackHander handler = it.next();
                handler.onUserJoined(uid, elapsed);
            }
        }

        @Override
        public void onUserOffline(int uid, int reason) {
            log.debug("onUserOffline " + (uid & 0xFFFFFFFFL) + " " + reason);

            // FIXME this callback may return times
            Iterator<ServerEngineEventHandler_ActivityCallBackHander> it = mEventHandlerList.keySet().iterator();
            while (it.hasNext()) {
                ServerEngineEventHandler_ActivityCallBackHander handler = it.next();
                handler.onUserOffline(uid, reason);
            }
        }

        @Override
        public void onUserMuteVideo(int uid, boolean muted) {
            log.debug("onUserMuteVideo " + (uid & 0xFFFFFFFFL) + " " + muted);

            Iterator<ServerEngineEventHandler_ActivityCallBackHander> it = mEventHandlerList.keySet().iterator();
            while (it.hasNext()) {
                ServerEngineEventHandler_ActivityCallBackHander handler = it.next();
                handler.onExtraCallback(ServerEngineEventHandler_ActivityCallBackHander.EVENT_TYPE_ON_USER_VIDEO_MUTED, uid, muted);
            }
        }

        @Override
        public void onRtcStats(RtcStats stats) {
        }

        @Override
        public void onRemoteVideoStats(RemoteVideoStats stats) {
//            log.debug("onRemoteVideoStats " + stats.uid + " " + stats.delay + " " + stats.receivedBitrate + " " + stats.receivedFrameRate + " " + stats.width + " " + stats.height);

            Iterator<ServerEngineEventHandler_ActivityCallBackHander> it = mEventHandlerList.keySet().iterator();
            while (it.hasNext()) {
                ServerEngineEventHandler_ActivityCallBackHander handler = it.next();
                handler.onExtraCallback(ServerEngineEventHandler_ActivityCallBackHander.EVENT_TYPE_ON_USER_VIDEO_STATS, stats);
            }
        }

        @Override
        public void onAudioVolumeIndication(AudioVolumeInfo[] speakerInfos, int totalVolume) {
            if (speakerInfos == null) {
                // quick and dirty fix for crash
                // TODO should reset UI for no sound
                return;
            }

            Iterator<ServerEngineEventHandler_ActivityCallBackHander> it = mEventHandlerList.keySet().iterator();
            while (it.hasNext()) {
                ServerEngineEventHandler_ActivityCallBackHander handler = it.next();
                handler.onExtraCallback(ServerEngineEventHandler_ActivityCallBackHander.EVENT_TYPE_ON_SPEAKER_STATS, (Object) speakerInfos);
            }
        }

        @Override
        public void onLeaveChannel(RtcStats stats) {
            log.debug("onLeaveChannel");

            Iterator<ServerEngineEventHandler_ActivityCallBackHander> it = mEventHandlerList.keySet().iterator();
            while (it.hasNext()) {
                ServerEngineEventHandler_ActivityCallBackHander handler = it.next();
                handler.onLeaveChannel();
            }
        }
        @Override
        public void onLastmileQuality(int quality) {

//            log.debug("onLastmileQuality " + quality);
        }

        @Override
        public void onError(int error) {
            log.debug("onError " + error + " " + RtcEngine.getErrorDescription(error));

            Iterator<ServerEngineEventHandler_ActivityCallBackHander> it = mEventHandlerList.keySet().iterator();
            while (it.hasNext()) {
                ServerEngineEventHandler_ActivityCallBackHander handler = it.next();
                handler.onExtraCallback(ServerEngineEventHandler_ActivityCallBackHander.EVENT_TYPE_ON_AGORA_MEDIA_ERROR, error, RtcEngine.getErrorDescription(error));
            }
        }

        @Override
        public void onStreamMessage(int uid, int streamId, byte[] data) {
            log.debug("onStreamMessage " + (uid & 0xFFFFFFFFL) + " " + streamId + " " + Arrays.toString(data));

            Iterator<ServerEngineEventHandler_ActivityCallBackHander> it = mEventHandlerList.keySet().iterator();
            while (it.hasNext()) {
                ServerEngineEventHandler_ActivityCallBackHander handler = it.next();
                handler.onExtraCallback(ServerEngineEventHandler_ActivityCallBackHander.EVENT_TYPE_ON_DATA_CHANNEL_MSG, uid, data);
            }
        }

        public void onStreamMessageError(int uid, int streamId, int error, int missed, int cached) {
            log.warn("onStreamMessageError " + (uid & 0xFFFFFFFFL) + " " + streamId + " " + error + " " + missed + " " + cached);

            Iterator<ServerEngineEventHandler_ActivityCallBackHander> it = mEventHandlerList.keySet().iterator();
            while (it.hasNext()) {
                ServerEngineEventHandler_ActivityCallBackHander handler = it.next();
                handler.onExtraCallback(ServerEngineEventHandler_ActivityCallBackHander.EVENT_TYPE_ON_AGORA_MEDIA_ERROR, error, "on stream msg error " + (uid & 0xFFFFFFFFL) + " " + missed + " " + cached);
            }
        }

        @Override
        public void onConnectionLost() {
            log.debug("onConnectionLost");

            Iterator<ServerEngineEventHandler_ActivityCallBackHander> it = mEventHandlerList.keySet().iterator();
            while (it.hasNext()) {
                ServerEngineEventHandler_ActivityCallBackHander handler = it.next();
                handler.onExtraCallback(ServerEngineEventHandler_ActivityCallBackHander.EVENT_TYPE_ON_APP_ERROR, ConstantApp.AppError.NO_NETWORK_CONNECTION);
            }
        }

        @Override
        public void onConnectionInterrupted() {
            log.debug("onConnectionInterrupted");

            Iterator<ServerEngineEventHandler_ActivityCallBackHander> it = mEventHandlerList.keySet().iterator();
            while (it.hasNext()) {
                ServerEngineEventHandler_ActivityCallBackHander handler = it.next();
                handler.onExtraCallback(ServerEngineEventHandler_ActivityCallBackHander.EVENT_TYPE_ON_APP_ERROR, ConstantApp.AppError.NO_NETWORK_CONNECTION);
            }
        }

        @Override
        public void onJoinChannelSuccess(String channel, int uid, int elapsed) {
            log.debug("onJoinChannelSuccess " + channel + " " + uid + " " + (uid & 0xFFFFFFFFL) + " " + elapsed);

            mConfig.mUid = uid;

            Iterator<ServerEngineEventHandler_ActivityCallBackHander> it = mEventHandlerList.keySet().iterator();
            while (it.hasNext()) {
                ServerEngineEventHandler_ActivityCallBackHander handler = it.next();
                handler.onJoinChannelSuccess(channel, uid, elapsed);
            }
        }

        public void onRejoinChannelSuccess(String channel, int uid, int elapsed) {
            log.debug("onRejoinChannelSuccess " + channel + " " + uid + " " + elapsed);
        }

        @Override
        public void onAudioRouteChanged(int routing) {
            log.debug("onAudioRouteChanged " + routing);

            Iterator<ServerEngineEventHandler_ActivityCallBackHander> it = mEventHandlerList.keySet().iterator();
            while (it.hasNext()) {
                ServerEngineEventHandler_ActivityCallBackHander handler = it.next();
                handler.onExtraCallback(ServerEngineEventHandler_ActivityCallBackHander.EVENT_TYPE_ON_AUDIO_ROUTE_CHANGED, routing);
            }
        }

        @Override
        public void onUserEnableLocalVideo(int uid, boolean enabled) {
            super.onUserEnableLocalVideo(uid, enabled);

            log.debug("onUserEnableLocalVideo " + uid + "enabled:" + enabled);

        }

        @Override
        public void onUserEnableVideo(int uid, boolean enabled) {
            super.onUserEnableVideo(uid, enabled);
            log.debug("onUserEnableVideo " + uid + "enabled:" + enabled);

        }

        public void onWarning(int warn) {
            log.debug("onWarning " + warn);
        }
    };

}