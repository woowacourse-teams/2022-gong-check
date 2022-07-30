import SectionDetailModal from '../SectionDetailModal';

import useModal from '@/hooks/useModal';

const useSectionCardDetail = () => {
  const { openModal } = useModal();

  const onClickSectionDetail = (sectionIndex: number) => {
    openModal(<SectionDetailModal />);
  };

  const onClickTaskDetail = (taskIndex: number) => {
    openModal(<SectionDetailModal />);
  };

  return { onClickSectionDetail, onClickTaskDetail };
};

export default useSectionCardDetail;
