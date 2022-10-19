import useNameModal from './useNameModal';

import Button from '@/components/common/Button';
import Dimmer from '@/components/common/Dimmer';
import Input from '@/components/common/Input';

import ModalPortal from '@/portals/ModalPortal';

import styles from './styles';

interface NameModalProps {
  title: string;
  detail: string;
  placeholder: string;
  buttonText: string;
  completeJobs: (name: string) => void;
}

const NameModal: React.FC<NameModalProps> = ({ title, detail, placeholder, buttonText, completeJobs }) => {
  const { name, isDisabledButton, onChangeInput, onClickButton } = useNameModal(completeJobs);

  return (
    <ModalPortal>
      <Dimmer>
        <div css={styles.container}>
          <h1 css={styles.title}>{title}</h1>
          <span css={styles.detail}>{detail}</span>
          <Input placeholder={placeholder} onChange={onChangeInput} value={name} maxLength={10} required />
          <Button css={styles.submitButton(isDisabledButton)} onClick={onClickButton} disabled={isDisabledButton}>
            {buttonText}
          </Button>
        </div>
      </Dimmer>
    </ModalPortal>
  );
};

export default NameModal;
