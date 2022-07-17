import React, { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

import Button from '@/components/_common/Button';

import apis from '@/apis';

import homeCover from '@/assets/homeCover.png';

import styles from './styles';

const Password: React.FC = () => {
  const navigate = useNavigate();

  const [isActiveSubmit, setIsActiveSubmit] = useState(false);
  const { hostId } = useParams();

  const setToken = async (password: string) => {
    const { token } = await apis.postPassword({ hostId, password });
    localStorage.setItem('user', token);
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
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

  const handelChangeInput = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { value } = e.target as HTMLInputElement;

    const isExistValue = value.length > 0;
    setIsActiveSubmit(isExistValue);
  };

  return (
    <div css={styles.layout}>
      <div>
        <img src={homeCover} alt="" />
      </div>
      <div css={styles.textWrapper}>
        <p>비밀번호를 입력해주세요.</p>
        <p>
          <span>관리자가 공유한 비밀번호입니다.</span>
          <span>비밀번호를 잊으셨나요?</span>
          <span>관리자에게 연락해보세요.</span>
        </p>
      </div>
      <form css={styles.form({ isActiveSubmit })} onSubmit={handleSubmit}>
        <input onChange={handelChangeInput} placeholder="비밀번호를 입력해주세요." required />
        <Button type="submit">확인</Button>
      </form>
    </div>
  );
};

export default Password;
