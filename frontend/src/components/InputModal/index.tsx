import { css } from '@emotion/react';
import { useState } from 'react';

import Button from '@/components/_common/Button';
import Dimmer from '@/components/_common/Dimmer';
import Input from '@/components/_common/Input';

import useModal from '@/hooks/useModal';

import apis from '@/apis';

import ModalPortal from '@/ModalPortal';

import theme from '@/styles/theme';

import styles from './styles';

interface InputModalProps {
  title: string;
  detail: string;
  placeholder: string;
  buttonText: string;
}

const InputModal = ({ title, detail, placeholder, buttonText }: InputModalProps) => {
  const { closeModal } = useModal();
  const [isDisabledButton, setIsDisabledButton] = useState(true);
  const [password, setPassword] = useState('');

  const setToken = async (password: string) => {
    const { token } = await apis.postPassword({ hostId: 1, password });
    localStorage.setItem('user', token);
  };

  const handleOnChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const isTyped = !!e.target.value;
    setPassword(e.target.value);
    setIsDisabledButton(!isTyped);
  };

  const handleClickButton = async () => {
    try {
      await setToken(password);
      closeModal();
      window.location.reload();
    } catch (err) {
      alert('비밀번호를 확인해주세요.');
    }
  };

  return (
    <ModalPortal>
      <Dimmer isAbleClick={false}>
        <div css={styles.container}>
          <h1 css={styles.title}>{title}</h1>
          <span css={styles.detail}>{detail}</span>
          <Input placeholder={placeholder} onChange={handleOnChange} value={password} />
          <Button
            css={css`
              margin-bottom: 0;
              width: 256px;
              background: ${isDisabledButton ? theme.colors.gray : theme.colors.primary};
            `}
            onClick={handleClickButton}
            disabled={isDisabledButton}
          >
            {buttonText}
          </Button>
        </div>
      </Dimmer>
    </ModalPortal>
  );
};

export default InputModal;
