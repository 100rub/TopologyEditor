package TopologyEditor.EventsHandling;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by VEF on 23.04.2015.
 */
public final class ActionHandler
{
    private Queue<ActionInfo> _actions;
    private Queue<ActionInfo> _undoes;



    public ActionHandler()
    {
        _actions = new LinkedList<ActionInfo>();
        _undoes = new LinkedList<ActionInfo>();
    }



    public void Do(Action action, ActionParameters parameters)
    {
        _actions.add(action.Apply(parameters));
        _undoes.clear();
    }

    public void Undo() throws Exception
    {
        if (_actions.isEmpty())
            throw new Exception("Nothing to undo");
        ActionInfo info = _actions.poll();
        _undoes.add(info.getOpposite().Apply(info.getParameters()));
    }

    public void Redo() throws Exception
    {
        if (_undoes.isEmpty())
            throw new Exception("Nothing to redo");
        ActionInfo info = _undoes.poll();
        _actions.add(info.getOpposite().Apply(info.getParameters()));
    }

    public Iterable<ActionInfo> getActions()
    {
        return _actions;
    }

    public ActionInfo getLastAction()
    {
        return _actions.peek();
    }

    public ActionInfo getLastUndo()
    {
        return _undoes.peek();
    }
}
