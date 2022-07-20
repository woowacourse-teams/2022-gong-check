import useNameModal from './useNameModal';
import { css } from '@emotion/react';

import Button from '@/components/common/Button';
import Dimmer from '@/components/common/Dimmer';
import Input from '@/components/common/Input';

import ModalPortal from '@/ModalPortal';

import theme from '@/styles/theme';

import styles from './styles';

interface NameModalProps {
  title: string;
  detail: string;
  placeholder: string;
  buttonText: string;
  jobId: string | undefined;
  hostId: string | undefined;
}

const NameModal: React.FC<NameModalProps> = ({ title, detail, placeholder, buttonText, jobId, hostId }) => {
  const { name, isDisabledButton, onChangeInput, onClickButton } = useNameModal(jobId, hostId);

  return (
    <ModalPortal>
      <Dimmer mode="mobile">
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
