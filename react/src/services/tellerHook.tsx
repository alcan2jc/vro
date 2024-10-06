import React, { useCallback, useState } from 'react';

import {
  useTellerConnect,
  TellerConnectOnSuccess,
  TellerConnectOnEvent,
  TellerConnectOnExit,
  TellerConnectOptions,
} from 'teller-connect-react';

const useTeller = () => {
  const applicationId = 'app_p4ne1aieggcd99f2e4000';
  const onSuccess = useCallback<TellerConnectOnSuccess>((authorization) => {
    // send public_token to your server
    // https://teller.io/docs/api/tokens/#token-exchange-flow
    console.log(authorization);
  }, []);
  const onEvent = useCallback<TellerConnectOnEvent>((name, data) => {
    console.log(name, data);
  }, []);
  const onExit = useCallback<TellerConnectOnExit>(() => {
    console.log("TellerConnect was dismissed by user");
  }, []);

  const config: TellerConnectOptions = {
    applicationId,
    onSuccess,
    onEvent,
    onExit,
  };

  const {
    open,
    ready,
  } = useTellerConnect(config);

  return { open, ready };
};

export default useTeller;