import { css } from '@emotion/react';
import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

import Button from '@/components/_common/Button';
import Dimmer from '@/components/_common/Dimmer';
import Input from '@/components/_common/Input';

import useModal from '@/hooks/useModal';

import apis from '@/apis';

import ModalPortal from '@/ModalPortal';

import theme from '@/styles/theme';

import styles from './styles';

interface NameModalProps {
  title: string;
  detail: string;
  placeholder: string;
  buttonText: string;
  jobId: string | undefined;
}

const NameModal = ({ title, detail, placeholder, buttonText, jobId }: NameModalProps) => {
  const navigate = useNavigate();

  const { closeModal } = useModal();
  const [isDisabledButton, setIsDisabledButton] = useState(true);
  const [name, setName] = useState('');
  const { hostId } = useParams();

  const handleOnChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const isTyped = !!e.target.value;
    setName(e.target.value);
    setIsDisabledButton(!isTyped);
  };

  const handleClickButton = async () => {
    try {
      await apis.postJobComplete({ jobId, author: name });
      alert('제출 되었습니다.');
      closeModal();
      navigate(`/${hostId}/spaces`);
    } catch (err) {
      alert(err);
    }
  };

  return (
    <ModalPortal>
      <Dimmer isAbleClick={false}>
        <div css={styles.container}>
          <h1 css={styles.title}>{title}</h1>
          <span css={styles.detail}>{detail}</span>
          <Input placeholder={placeholder} onChange={handleOnChange} value={name} />
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

export default NameModal;
