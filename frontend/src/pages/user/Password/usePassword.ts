import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

import useToast from '@/hooks/useToast';

import apis from '@/apis';

import { ID } from '@/types';

const usePassword = () => {
  const navigate = useNavigate();

  const { openToast } = useToast();

  const [isActiveSubmit, setIsActiveSubmit] = useState(false);
  const { hostId } = useParams() as { hostId: ID };

  const setToken = async (password: string) => {
    if (!hostId) return;
    const { token } = await apis.postPassword(hostId!, password);
    localStorage.setItem('token', token);
  };

  const onSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (!isActiveSubmit) return;

    const form = e.target as HTMLFormElement;
    const { value: password } = form[0] as HTMLInputElement;

    try {
      await setToken(password).then(() => {
        navigate(`/enter/${hostId}/spaces`);
      });
    } catch (err) {
      openToast('ERROR', '비밀번호를 확인해주세요.');
    }
  };

  const onChangeInput = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { value } = e.target as HTMLInputElement;

    const isExistValue = value.length > 0;
    setIsActiveSubmit(isExistValue);
  };

  return {
    isActiveSubmit,
    onSubmit,
    onChangeInput,
  };
};

export default usePassword;
