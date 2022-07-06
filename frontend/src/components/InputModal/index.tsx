/**  @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import React, { useState } from 'react';
import theme from '../../styles/theme';
import Button from '../_common/Button';
import Input from '../_common/Input';
import styles from './styles';
import ModalPortal from '../../ModalPortal';
import Dimmer from '../_common/Dimmer';

interface InputModalProps {
  title: string;
  detail: string;
  placeholder: string;
  buttonText: string;
  closeModal: () => void;
  requiredSubmit?: boolean;
}

const InputModal = ({
  title,
  detail,
  placeholder,
  buttonText,
  closeModal,
  requiredSubmit = false,
}: InputModalProps) => {
  const [isDisabledButton, setIsDisabledButton] = useState(true);
  const handleOnChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const isTyped = !!e.target.value;

    setIsDisabledButton(!isTyped);
  };
  return (
    <ModalPortal>
      <Dimmer closeModal={closeModal} requiredSubmit={requiredSubmit}>
        <div css={styles.container}>
          <h1 css={styles.title}>{title}</h1>
          <span css={styles.detail}>{detail}</span>
          <Input placeholder={placeholder} onChange={handleOnChange} />
          <Button
            css={css`
              margin-bottom: 0;
              width: 256px;
              background: ${isDisabledButton ? theme.colors.gray : theme.colors.primary};
            `}
            onClick={closeModal}
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
