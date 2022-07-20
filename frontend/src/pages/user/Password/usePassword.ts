import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

import apis from '@/apis';

const usePassword = () => {
  const navigate = useNavigate();

  const [isActiveSubmit, setIsActiveSubmit] = useState(false);
  const { hostId } = useParams();

  const setToken = async (password: string) => {
    const { token } = await apis.postPassword({ hostId, password });
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
      alert('비밀번호를 확인해주세요.');
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
