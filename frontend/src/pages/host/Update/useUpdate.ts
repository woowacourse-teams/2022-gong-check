import { AxiosError } from 'axios';
import { ChangeEvent, FormEvent, useState } from 'react';
import { useMutation } from 'react-query';
import { useNavigate } from 'react-router-dom';

import useToast from '@/hooks/useToast';

import apiHost from '@/apis/host';
import apiPassword from '@/apis/password';

import errorMessage from '@/constants/errorMessage';

const useUpdate = () => {
  const { openToast } = useToast();

  const [nickname, setNickname] = useState('');
  const [password, setPassword] = useState('');
  const [isShowPassword, setIsShowPassword] = useState(false);

  const { mutate: updatePassword } = useMutation(() => apiPassword.patchSpacePassword(password), {
    onSuccess: () => {
      openToast('SUCCESS', '비밀번호 변경에 성공하였습니다.');
    },
    onError: (err: AxiosError<{ errorCode: keyof typeof errorMessage }>) => {
      openToast('ERROR', errorMessage[`${err.response?.data.errorCode!}`]);
    },
  });

  const { mutate: updateNickname } = useMutation(() => apiHost.putHostProfile(nickname), {
    onSuccess: () => {
      openToast('SUCCESS', '닉네임 변경에 성공하였습니다.');
    },
    onError: (err: AxiosError<{ errorCode: keyof typeof errorMessage }>) => {
      openToast('ERROR', errorMessage[`${err.response?.data.errorCode!}`]);
    },
  });

  const onChangeNickname = (e: React.ChangeEvent<HTMLInputElement>) => {
    setNickname(e.target.value);
  };

  const onChangePassword = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value);
  };

  const onClickToggleShowPassword = () => {
    setIsShowPassword(prev => !prev);
  };

  const onSubmitChangePassword = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    updatePassword();
  };

  const onSubmitChangeNickname = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    updateNickname();
  };

  return {
    nickname,
    onChangeNickname,
    password,
    isShowPassword,
    onChangePassword,
    onClickToggleShowPassword,
    onSubmitChangePassword,
    onSubmitChangeNickname,
  };
};

export default useUpdate;
