import { useMutation, useQuery } from 'react-query';
import { useNavigate, useParams } from 'react-router-dom';

import SlackUrlModal from '@/components/host/SlackUrlModal';
import SpaceDeleteModal from '@/components/host/SpaceDeleteModal';

import useModal from '@/hooks/useModal';
import useToast from '@/hooks/useToast';

import apiHost from '@/apis/host';
import apiJobs from '@/apis/job';
import apiSpace from '@/apis/space';
import apiSubmission from '@/apis/submission';

import { ID } from '@/types';

const useDashBoard = () => {
  const navigate = useNavigate();

  const { openModal, closeModal } = useModal();
  const { openToast } = useToast();

  const { spaceId } = useParams() as { spaceId: ID };

  const { data: spaceData } = useQuery(['space', spaceId], () => apiSpace.getSpace(spaceId), {
    staleTime: 0,
    cacheTime: 0,
  });
  const { data: jobsData } = useQuery(['jobs', spaceId], () => apiJobs.getJobs(spaceId));
  const { data: submissionData } = useQuery(['submissions', spaceId], () => apiSubmission.getSubmission(spaceId));
  const { refetch: copyEntranceLink } = useQuery(['entranceCode'], () => apiHost.getEntranceCode(), {
    suspense: false,
    enabled: false,
    onSuccess: data => {
      const { entranceCode } = data;
      navigator.clipboard
        .writeText(`${window.location.origin}/enter/${entranceCode}/pwd`)
        .then(() => {
          openToast('SUCCESS', '공간 입장 링크가 복사되었습니다.');
        })
        .catch(() => {
          openToast('ERROR', '다시 시도해주세요.');
        });
    },
    onError: () => {
      openToast('ERROR', '잠시 후 다시 시도해주세요.');
    },
  });

  const { refetch } = useQuery(['deleteSpaces'], apiSpace.getSpaces, {
    enabled: false,
    onSuccess: data => {
      const { spaces } = data;

      if (spaces.length === 0) {
        navigate('/host/manage/spaceCreate');
        return;
      }

      const space = spaces[0];
      navigate(`/host/manage/${space.id}`);
    },
  });

  const { mutate: deleteSpace } = useMutation((spaceId: ID) => apiSpace.deleteSpace(spaceId), {
    onSuccess: () => {
      refetch();
      closeModal();
      openToast('SUCCESS', '공간이 삭제되었습니다.');
    },
  });

  const onClickSlackButton = () => {
    openModal(<SlackUrlModal jobs={jobsData?.jobs || []} />);
  };

  const onClickLinkButton = () => {
    copyEntranceLink();
  };

  const onClickDeleteSpace = () => {
    openModal(
      <SpaceDeleteModal text={`${spaceData?.name} 공간을 삭제합니다` || ''} onClick={() => deleteSpace(spaceId)} />
    );
  };

  return {
    spaceId,
    spaceData,
    jobsData,
    submissionData,
    onClickSlackButton,
    onClickLinkButton,
    onClickDeleteSpace,
  };
};

export default useDashBoard;
