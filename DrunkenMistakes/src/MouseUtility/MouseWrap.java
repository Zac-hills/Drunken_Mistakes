package MouseUtility;

import java.awt.Robot;
import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.event.InputEvent;
import java.util.Random;
import java.awt.Point;
import Windows.Box;

public class MouseWrap{
static Robot m_robot;
static public float m_MinWait = 0;
static public float m_MaxWait = .2f;
static Random m_random;
static public float m_MinCurve = 50.0f;
static public float m_MaxCurve = 100.0f;

static
{
try {
m_robot = new Robot();
} catch (AWTException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
m_random = new Random();
}

static public void AdjustMinMaxWait(float a_Min, float a_Max) {
m_MinWait = a_Min;
m_MaxWait = a_Max;
}

static public float ReturnWaitTime() {
return m_MinWait + (m_MaxWait - m_MinWait) * m_random.nextFloat();
}

static public void Wait() {
try {
Thread.sleep((long) (ReturnWaitTime() * 1000f));
} catch (InterruptedException e) {
Thread.currentThread().interrupt();
}
}

static public void Sleep(float Min, float Max) {
try {
Thread.sleep((long) ((m_random.nextFloat() * (Max - Min) + Min) * 1000f));
} catch (InterruptedException e) {
Thread.currentThread().interrupt();
}
}

static public void MoveMouse(Box a_box) {
Point f_temp = new Point();
f_temp = a_box.getRandomPoint();
m_robot.mouseMove(f_temp.x, f_temp.y);
}

static public void LeftClick() {
m_robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
Wait();
m_robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
}

static public void RightClick() {
m_robot.mousePress(InputEvent.BUTTON2_DOWN_MASK);
Wait();
m_robot.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
}

static public void LeftClickBox(Box a_box) {
MoveMouse(a_box);
LeftClick();
}

static public void RightClickBox(Box a_box) {
MoveMouse(a_box);
RightClick();
}

static public float LerpEase(float a_f1, float a_f2, float a_Time) {

return (1 - EaseTime(a_Time)) * a_f1 + EaseTime(a_Time) * a_f2;
}
static public float Lerp(float a_f1, float a_f2, float a_Time) {

return (1 - a_Time) * a_f1 + a_Time * a_f2;
}
static public Point Lerp(Point a_p1, Point a_p2, float a_Time) {
Point Temp = new Point();
Temp.x = (int) LerpEase(a_p1.x, a_p2.x, a_Time);
Temp.y = (int) LerpEase(a_p1.y, a_p2.y, a_Time);
return Temp;
}

static public void MoveMouse(Point a_P, float a_AmountOfTime) {
long InitialTime = System.currentTimeMillis();
long EndTime = InitialTime + (long) (a_AmountOfTime * 1000);
Point InitialLocation = MouseInfo.getPointerInfo().getLocation();
int Sign;
float MinCurve = Lerp(-90.0f,90.0f,m_random.nextFloat());
float MaxCurve = Lerp(-90.0f,90.0f,m_random.nextFloat());
if(m_random.nextBoolean())
{
Sign = 1;
}
else
{
Sign = -1;
}
float l_CurveCoefficient = (m_MinCurve + (m_random.nextFloat() * (m_MaxCurve - m_MinCurve)) * Sign);
while (System.currentTimeMillis() < EndTime) {
float timeCache = (System.currentTimeMillis() - InitialTime) / (a_AmountOfTime * 1000);
Point p = Lerp(InitialLocation, a_P, timeCache);
p.y += CurveLine(MinCurve, MaxCurve,timeCache) * l_CurveCoefficient;
m_robot.mouseMove(p.x, p.y);
}
LeftClick();
}
static private double CurveLine(float a_minCurve, float a_maxCurve, float time)
{
return Math.cos(Math.toRadians((double)Lerp(a_minCurve, a_maxCurve,time)));
}

static public void MoveMouseLeftClick(Box a_box, float a_Time) {
MoveMouse(a_box.getRandomPoint(), a_Time);
}

static float EaseTime(float t) {
float sqt = t * t;
return sqt / (2.0f * (sqt - t) + 1.0f);
}

}

