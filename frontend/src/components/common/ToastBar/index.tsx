import ToastPortal from '@/ToastPortal';
import { AiOutlineCheckCircle } from 'react-icons/ai';
import { BiError } from 'react-icons/bi';
import { useRecoilValue } from 'recoil';

import { toastState } from '@/recoil/toast';

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
