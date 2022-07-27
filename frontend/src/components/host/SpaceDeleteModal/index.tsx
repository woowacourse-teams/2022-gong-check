import React, { useState } from 'react';

import Button from '@/components/common/Button';
import Dimmer from '@/components/common/Dimmer';

import ModalPortal from '@/ModalPortal';

import styles from './styles';

interface SpaceDeleteModalProps {
  text: string | undefined;
  onClick: () => void;
}

const SpaceDeleteModal: React.FC<SpaceDeleteModalProps> = ({ text, onClick }) => {
  const [isDisabledButton, setIsDisabledButton] = useState(true);

  const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const isSameText = text === e.target.value;

    setIsDisabledButton(!isSameText);
  };

  return (
    <ModalPortal>
      <Dimmer mode="full">
        <div css={styles.container}>
          <div css={styles.textWrapper}>
            <span>다음 내용을 타이핑 하시면 공간을 삭제 할 수 있습니다.</span>
            <span>{text}</span>
          </div>
          <input css={styles.input} onChange={onChange} type="text" />
          <Button css={styles.button(isDisabledButton)} onClick={onClick} disabled={isDisabledButton}>
            삭제된 공간은 복구되지 않습니다.
          </Button>
        </div>
      </Dimmer>
    </ModalPortal>
  );
};

export default SpaceDeleteModal;
