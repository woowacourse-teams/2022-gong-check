import { AxiosError } from 'axios';
import { useState } from 'react';
import { useMutation } from 'react-query';
import { useNavigate } from 'react-router-dom';

import useToast from '@/hooks/useToast';

import apiPassword from '@/apis/password';

import errorMessage from '@/constants/errorMessage';

const usePasswordUpdate = () => {
  const navigate = useNavigate();

  const { openToast } = useToast();

  const [password, setPassword] = useState('');
  const [isShowPassword, setIsShowPassword] = useState(false);

  const { mutate: updatePassword } = useMutation(() => apiPassword.patchSpacePassword(password), {
    onSuccess: () => {
      openToast('SUCCESS', '비밀번호 변경에 성공하였습니다.');
      navigate(-1);
    },
    onError: (err: AxiosError<{ errorCode: keyof typeof errorMessage }>) => {
      openToast('ERROR', errorMessage[`${err.response?.data.errorCode!}`]);
    },
  });

  const onChangePassword = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value);
  };

  const onClickToggleShowPassword = () => {
    setIsShowPassword(prev => !prev);
  };

  const onClickChangeButton = () => {
    updatePassword();
  };

  return { password, isShowPassword, onChangePassword, onClickToggleShowPassword, onClickChangeButton };
};

export default usePasswordUpdate;
