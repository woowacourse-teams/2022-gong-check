import useNameModal from './useNameModal';

import Button from '@/components/common/Button';
import Dimmer from '@/components/common/Dimmer';
import Input from '@/components/common/Input';

import { ID } from '@/types';

import ModalPortal from '@/portals/ModalPortal';

import styles from './styles';

interface NameModalProps {
  title: string;
  detail: string;
  placeholder: string;
  buttonText: string;
  jobId: ID;
}

const NameModal: React.FC<NameModalProps> = ({ title, detail, placeholder, buttonText, jobId }) => {
  const { name, isDisabledButton, onChangeInput, onClickButton } = useNameModal(jobId);

  return (
    <ModalPortal>
      <Dimmer mode="mobile">
        <div css={styles.container}>
          <h1 css={styles.title}>{title}</h1>
          <span css={styles.detail}>{detail}</span>
          <Input placeholder={placeholder} onChange={onChangeInput} value={name} required />
          <Button css={styles.submitButton(isDisabledButton)} onClick={onClickButton} disabled={isDisabledButton}>
            {buttonText}
          </Button>
        </div>
      </Dimmer>
    </ModalPortal>
  );
};

export default NameModal;
