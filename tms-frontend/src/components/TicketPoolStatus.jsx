// eslint-disable-next-line react/prop-types
function TicketPoolStatus({totalTickets}) {

  return (
    <div className="bg-blue-500 rounded-lg p-5 shadow-md text-white">
      <h2 className="text-lg font-bold mb-4">Ticket Pool Status</h2>
      <p className="text-2xl font-bold">
        Total ticket count: <span className="text-yellow-400">{totalTickets}</span>
      </p>
    </div>
  );
};

export default TicketPoolStatus;
