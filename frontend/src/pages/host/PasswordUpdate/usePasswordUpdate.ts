import { AxiosError } from 'axios';
import { useState } from 'react';
import { useMutation } from 'react-query';

import useToast from '@/hooks/useToast';

import apiPassword from '@/apis/password';

const usePasswordUpdate = () => {
  const { openToast } = useToast();

  const [password, setPassword] = useState('');
  const [isShowPassword, setIsShowPassword] = useState(false);

  const { mutate: updatePassword } = useMutation(() => apiPassword.patchSpacePassword(password), {
    onSuccess: () => {
      openToast('SUCCESS', '비밀번호 변경에 성공하였습니다.');
    },
    onError: (err: AxiosError<{ message: string }>) => {
      openToast('ERROR', `${err.response?.data.message}`);
    },
  });

  const onChangePassword = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value);
  };

  const onClickFlipShowPassword = () => {
    setIsShowPassword(prev => !prev);
  };

  const onClickChangeButton = () => {
    updatePassword();
  };

  return { password, isShowPassword, onChangePassword, onClickFlipShowPassword, onClickChangeButton };
};

export default usePasswordUpdate;
