import useNameModal from './useNameModal';
import { css } from '@emotion/react';

import Button from '@/components/_common/Button';
import Dimmer from '@/components/_common/Dimmer';
import Input from '@/components/_common/Input';

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

const NameModal: React.FC<NameModalProps> = ({ title, detail, placeholder, buttonText, jobId }) => {
  const { name, isDisabledButton, onChangeInput, onClickButton } = useNameModal(jobId);

  return (
    <ModalPortal>
      <Dimmer>
        <div css={styles.container}>
          <h1 css={styles.title}>{title}</h1>
          <span css={styles.detail}>{detail}</span>
          <Input placeholder={placeholder} onChange={onChangeInput} value={name} />
          <Button
            css={css`
              margin-bottom: 0;
              width: 256px;
              background: ${isDisabledButton ? theme.colors.gray400 : theme.colors.primary};
            `}
            onClick={onClickButton}
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
