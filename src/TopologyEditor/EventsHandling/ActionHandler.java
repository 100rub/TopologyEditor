package TopologyEditor.EventsHandling;

import TopologyEditor.EventsHandling.Listeners.IDoListener;

import java.util.*;

/**
 * Created by VEF on 23.04.2015.
 */
public final class ActionHandler
{
    private Stack<ActionInfo> _actions;
    private Stack<ActionInfo> _undoes;

    private List<IDoListener> _onDo;
    private List<IDoListener> _onUnDo;
    private List<IDoListener> _onReDo;



    public ActionHandler()
    {
        _actions = new Stack<ActionInfo>();
        _undoes = new Stack<ActionInfo>();

        _onDo = new ArrayList<IDoListener>();
        _onUnDo = new ArrayList<IDoListener>();
        _onReDo = new ArrayList<IDoListener>();
    }



    public void Do(Action action, ActionParameters parameters)
    {
        _actions.add(action.Apply(parameters));
        for (IDoListener listener : _onDo)
        {
            listener.OnDo(this, action, parameters);
        }
        _undoes.clear();
    }

    public boolean Undo()
    {
        if (_actions.isEmpty())
            return false;
        ActionInfo info = _actions.pop();
        Action action = info.GetOpposite();
        ActionParameters parameters = info.GetParameters();

        _undoes.add(action.Apply(parameters));
        for (IDoListener listener : _onDo)
        {
            listener.OnDo(this, action, parameters);
        }
        return true;
    }

    public boolean Redo()
    {
        if (_undoes.isEmpty())
            return false;
        ActionInfo info = _undoes.pop();
        Action action = info.GetOpposite();
        ActionParameters parameters = info.GetParameters();

        _actions.add(action.Apply(parameters));
        for (IDoListener listener : _onDo)
        {
            listener.OnDo(this, action, parameters);
        }
        return true;
    }



    public Collection<ActionInfo> GetActions()
    {
        return _actions;
    }

    public ActionInfo GetLastAction()
    {
        return _actions.isEmpty() ? null : _actions.peek();
    }

    public ActionInfo GetLastUndo()
    {
        return _undoes.isEmpty() ? null : _undoes.peek();
    }



    public boolean RegisterDoListener(IDoListener listener)
    {
        return _onDo.add(listener);
    }

    public boolean RegisterUnDoListener(IDoListener listener)
    {
        return _onUnDo.add(listener);
    }

    public boolean RegisterReDoListener(IDoListener listener)
    {
        return _onReDo.add(listener);
    }
}
