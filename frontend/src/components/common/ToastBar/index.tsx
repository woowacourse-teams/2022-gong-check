import ToastPortal from '@/ToastPortal';
import { AiOutlineCheckCircle } from 'react-icons/ai';
import { BiError } from 'react-icons/bi';
import { useRecoilValue } from 'recoil';

import { toastState } from '@/recoil/modal';

import styles from './styles';

const ToastBar: React.FC = () => {
  const state = useRecoilValue(toastState);

  return (
    <ToastPortal>
      <div css={styles.layout(state.type)}>
        {state.type === 'SUCCESS' ? <AiOutlineCheckCircle size={20} /> : <BiError size={20} />}
        <span css={styles.text}>{state.text}</span>
      </div>
    </ToastPortal>
  );
};

export default ToastBar;
