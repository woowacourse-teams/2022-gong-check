import ToastPortal from '@/ToastPortal';
import { AiOutlineCheckCircle } from 'react-icons/ai';
import { BiError } from 'react-icons/bi';

import styles from './styles';

interface ToastBarProps {
  type: 'SUCCESS' | 'ERROR';
  text: string;
}

const ToastBar: React.FC<ToastBarProps> = ({ type, text }) => {
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
