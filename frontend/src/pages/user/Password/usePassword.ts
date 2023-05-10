import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

import useToast from '@/hooks/useToast';

import apis from '@/apis';

import { ID } from '@/types';

const usePassword = () => {
  const navigate = useNavigate();

  const { openToast } = useToast();

  const { hostId } = useParams() as { hostId: ID };

  const [isActiveSubmit, setIsActiveSubmit] = useState(false);

  const setToken = async (password: string) => {
    const { token } = await apis.postPassword(hostId, password);
    localStorage.setItem(`${hostId}`, token);
    sessionStorage.setItem('tokenKey', `${hostId}`);
  };

  const onSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (!isActiveSubmit) return;

    const form = e.target as HTMLFormElement;
    const password = form['password'].value;

    try {
      await setToken(password).then(() => {
        navigate(`/enter/${hostId}/spaces`);
      });
    } catch (err) {
      openToast('ERROR', '비밀번호를 확인해주세요.');
    }
  };

  const onChangeInput = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { value: password } = e.target;

    const isTyped = password.length > 0;
    setIsActiveSubmit(isTyped);
  };

  return {
    isActiveSubmit,
    onSubmit,
    onChangeInput,
    hostId,
  };
};

export default usePassword;
