import ReactDOM from 'react-dom';

interface ModalPortalProps {
  children: React.ReactNode;
}

const ModalPortal = ({ children }: ModalPortalProps) => {
  return ReactDOM.createPortal(children, document.getElementById('modal') as HTMLElement);
};

export default ModalPortal;
