import { AiOutlineCheckCircle } from '@react-icons/all-files/ai/AiOutlineCheckCircle';
import { BiError } from '@react-icons/all-files/bi/BiError';
import { useRecoilValue } from 'recoil';

import { toastState } from '@/recoil/toast';

import ToastPortal from '@/portals/ToastPortal';

import styles from './styles';

const ToastBar: React.FC = () => {
  const { type, text } = useRecoilValue(toastState);

  return (
    <ToastPortal>
      <div css={styles.layout(type)}>
        {type === 'SUCCESS' ? <AiOutlineCheckCircle size={20} /> : <BiError size={20} />}
        <span css={styles.text}>{text}</span>
      </div>
    </ToastPortal>
  );
};

export default ToastBar;
