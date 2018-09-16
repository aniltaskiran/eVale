package Controller;

import model.ResponseType;
import model.Sms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResponseController {
    HttpServletResponse response;
    HttpServletRequest request;

    public ResponseController(HttpServletResponse response, HttpServletRequest request) {
        this.response = response;
        this.request = request;
    }


    public void sendResponse(ResponseType type) {
            switch (type) {
                case GetValetInfo:
                    new ValetController().getValetInfoFromPhone(request, response);
                    break;
                case UpdateValetInfo:
                    new ValetController().updateValetInfo(request, response);
                    break;
                case CheckCarIsAvailable:
                    new CarController().checkRegistrationAvailability(request, response);
                    break;
                case RegisterNewCar:
                    new CarController().registerNewCar(request, response);
                    break;
                case ZoneWaitingCarList:
                    new ValetController().getZoneWaitingCarList(request, response);
                    break;
                case SetZoneToCar:
                    new CarController().setZoneToCar(request, response);
                    break;
                case DeliveryWaitingList:
                    new ValetController().getDeliveryWaitingList(request, response);
                    break;
                case SetCarStatus:
                    new CarController().setCarStatus(request, response);
                    break;
                case saveTipAndMoveToLog:
                    new ValetController().saveTipAndMoveToLog(request, response);
                    break;
                case SmsServlet:
                    Sms sms = new Sms();
                    new SmsController().smsSend(sms, response);
                case WrongEntity:
                    new CarController().wrongEntity(request,response);
            }
    }
}
