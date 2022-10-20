import { Stomp } from '@stomp/stompjs';
import { useRef } from 'react';

const useSocket = (url: string) => {
  const stomp = useRef<any>(null);
  const isConnected = useRef<boolean>(false);

  const checkIsConnect = () => {
    return isConnected.current;
  };
  const connectSocket = (callbackConnect: () => void, callbackError: () => void, callbackDisconnect: () => void) => {
    stomp.current = Stomp.client(url);
    isConnected.current = true;

    stomp.current.reconnect_delay = 1000;
    stomp.current.heartbeat.outgoing = 0;
    stomp.current.heartbeat.incoming = 0;

    if (process.env.NODE_ENV !== 'development') {
      stomp.current.debug = () => {};
    }

    stomp.current.connect({}, callbackConnect, callbackError, callbackDisconnect);
  };

  const disconnectSocket = () => {
    if (isConnected.current) {
      isConnected.current = false;
      stomp.current.disconnect();
    }
  };

  const subscribeTopic = (endPoint: string, callback: (data?: unknown) => void) => {
    stomp.current.subscribe(endPoint, callback);
  };

  const sendMessage = (endPoint: string, data: unknown) => {
    stomp.current.send(endPoint, {}, JSON.stringify(data));
  };

  return {
    checkIsConnect,
    connectSocket,
    disconnectSocket,
    subscribeTopic,
    sendMessage,
  };
};

export default useSocket;
