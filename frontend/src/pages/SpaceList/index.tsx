const spaces = ['선릉', '잠실'];

const SpaceList = () => {
  return (
    <>
      {spaces.map((space, index) => (
        <div key={index}>{space}</div>
      ))}
    </>
  );
};

export default SpaceList;
